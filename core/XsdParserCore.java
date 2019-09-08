package proholz.xsdparser;


public abstract class XsdParserCore {

    /**
     * A {@link Map} object that contains a parse function to each {@link XsdAbstractElement} concrete
     * type supported by this mapper, this way based on the concrete {@link XsdAbstractElement} tag the according parse
     * method can be invoked.
     */
    static Dictionary<String, BiFunction<XsdParserCore, XmlElement, ReferenceBase>> parseMappers;

    /**
     * A {@link Map} object that contains the all the XSD types and their respective types in the Java
     * language.
     */
    private static Dictionary<String, String> xsdTypesToJava;

    /**
     * A {@link List} which contains all the top elements parsed by this class.
     */
    private Dictionary<String, List<ReferenceBase>> parseElements = new Dictionary<String, List<ReferenceBase>>();

    /**
     * A {@link List} of {@link UnsolvedReference} elements that weren't solved. This list is consulted after all the
     * elements are parsed in order to find if there is any suitable parsed element to replace the unsolved element.
     */
    private Dictionary<String, List<UnsolvedReference>> unsolvedElements = new Dictionary<String, List<UnsolvedReference>>();

    /**
     * A {@link List} containing all the elements that even after parsing all the elements on the file, don't have a
     * suitable object to replace the reference. This list can be consulted after the parsing process to assert if there
     * is any missing information in the XSD file.
     */
    private List<UnsolvedReferenceItem> parserUnsolvedElementsMap = new List<UnsolvedReferenceItem>();

    /**
     * A {@link List} containing the paths of files that were present in either {@link XsdInclude} or {@link XsdImport}
     * objects that are present in the original or subsequent files. These paths are stored to be parsed as well, the
     * parsing process only ends when all the files present in this {@link List} are parsed.
     */
    List<String> schemaLocations = new List<String>();

    String currentFile;

    static {
        DefaultParserConfig config = new DefaultParserConfig();

        parseMappers = config.getParseMappers();
        xsdTypesToJava = config.getXsdTypesToJava();
    }

    /**
     * Verifies if a given {@link XmlElement} object, i.e. {@code node} is a xsd:schema node.
     * @param node The node to verify.
     * @return True if the node is a xsd:schema or xs:schema. False otherwise.
     */
    boolean isXsdSchema(XmlElement node){
        String schemaNodeName = node.getNodeName();

        return schemaNodeName.equals(XsdSchema.XSD_TAG) || schemaNodeName.equals(XsdSchema.XS_TAG);
    }

    /**
     * This method resolves all the remaining {@link UnsolvedReference} objects present after all the elements are parsed.
     * It starts by iterating all {@link XsdParser#parseElements} and inserting all the parsed elements with a name
     * attribute in the concreteElementsMap variable. After that it iterates on the {@link XsdParser#unsolvedElements}
     * list in order to find if any of the unsolvedReferences can be solved by replacing the unsolvedElement by its
     * matching {@link NamedConcreteElement} object, present in the concreteElementsMap. The {@link UnsolvedReference}
     * objects matches a {@link NamedConcreteElement} object by having its ref attribute with the same value as the
     * name attribute of the {@link NamedConcreteElement}.
     */
    void resolveRefs() {
        resolveInnerRefs();
        resolveOtherNamespaceRefs();
    }

    private void resolveOtherNamespaceRefs() {
        parseElements
                .keySet()
                .ForEach(fileName -> {
                    XsdSchema xsdSchema =
                            parseElements.get(fileName)
                                    .stream()
                                    .filter(referenceBase -> referenceBase instanceof ConcreteElement && referenceBase.getElement() instanceof XsdSchema)
                                    .Select(referenceBase -> (((XsdSchema) referenceBase.getElement())))
                                    .findFirst()
                                    .get();

                    Dictionary<String, NamespaceInfo> ns = xsdSchema.getNamespaces();

                    unsolvedElements
                            .getOrDefault(fileName, new List<String>())
                            .stream()
                            .filter(unsolvedElement -> unsolvedElement.getRef().Contains(":"))
                            .ForEach(unsolvedElement -> {
                                String unsolvedElementNamespace = unsolvedElement.getRef().Substring(0, unsolvedElement.getRef().indexOf(":"));

                                Optional<String> foundNamespaceId = ns.keySet().stream().filter(namespaceId -> namespaceId.equals(unsolvedElementNamespace)).findFirst();

                                if (foundNamespaceId.isPresent()){
                                    String importedFileLocation = ns.get(foundNamespaceId.get()).getFile();

                                    String importedFileName = importedFileLocation.Substring(importedFileLocation.LastIndexOf("/")+1);

                                    List<ReferenceBase> importedElements = parseElements.getOrDefault(importedFileLocation, parseElements.get(parseElements.keySet().stream().filter(k -> k.endsWith(importedFileName)).findFirst().get()));

                                    Dictionary<String, List<NamedConcreteElement>> concreteElementsMap =
                                            importedElements.stream()
                                                    .filter(concreteElement -> concreteElement instanceof NamedConcreteElement)
                                                    .Select(concreteElement -> (NamedConcreteElement) concreteElement)
                                                    .collect(groupingBy(NamedConcreteElement::getName));

                                    replaceUnsolvedImportedReference(concreteElementsMap, unsolvedElement);
                                }
                            });
                });
    }

    private void replaceUnsolvedImportedReference(Dictionary<String, List<NamedConcreteElement>> concreteElementsMap, UnsolvedReference unsolvedReference) {
        List<NamedConcreteElement> concreteElements = concreteElementsMap.get(unsolvedReference.getRef().Substring(unsolvedReference.getRef().IndexOf(":") + 1));

        if (concreteElements != null){
            Dictionary<String, String> oldElementAttributes = unsolvedReference.getElement().getAttributesMap();

            for (NamedConcreteElement concreteElement : concreteElements) {
                NamedConcreteElement substitutionElementWrapper;

                if (!unsolvedReference.isTypeRef()){
                    XsdNamedElements substitutionElement = concreteElement.getElement().clone(oldElementAttributes);

                    substitutionElementWrapper = (NamedConcreteElement) ReferenceBase.createFromXsd(substitutionElement);
                } else {
                    substitutionElementWrapper = concreteElement;
                }

                unsolvedReference.getParent().replaceUnsolvedElements(substitutionElementWrapper);
            }
        } else {
            storeUnsolvedItem(unsolvedReference);
        }
    }

    private void resolveInnerRefs() {
        parseElements
            .keySet()
            .ForEach(fileName -> {
                List<String> includedFiles =
                        parseElements.get(fileName)
                                    .stream()
                                    .filter(referenceBase -> referenceBase instanceof ConcreteElement && referenceBase.getElement() instanceof XsdInclude)
                                    .Select(referenceBase -> (((XsdInclude) referenceBase.getElement()).getSchemaLocation()))
                                    .collect(Collectors.toList());

                                    List<ReferenceBase> includedElements = new List<ReferenceBase>(parseElements.get(fileName));

                includedFiles.ForEach(includedFile ->{
                    String includedFilename = includedFile.Substring(includedFile.LastIndexOf("/")+1);

                    includedElements.Add(parseElements.getOrDefault(includedFile, parseElements.get(parseElements.keySet().stream().filter(k -> k.endsWith(includedFilename)).findFirst().get())));
                });

                Dictionary<String, List<NamedConcreteElement>> concreteElementsMap =
                        includedElements.stream()
                                .filter(concreteElement -> concreteElement instanceof NamedConcreteElement)
                                .Select(concreteElement -> (NamedConcreteElement) concreteElement)
                                .collect(groupingBy(NamedConcreteElement::getName));

                                unsolvedElements.getOrDefault(fileName, new List<ReferenceBase>())
                        .stream()
                        .filter(unsolvedElement -> !unsolvedElement.getRef().Contains(":"))
                        .ForEach(unsolvedElement -> replaceUnsolvedReference(concreteElementsMap, unsolvedElement));
            });
    }

    /**
     * Replaces a single {@link UnsolvedReference} object, with the respective {@link NamedConcreteElement} object. If
     * there isn't a {@link NamedConcreteElement} object to replace the {@link UnsolvedReference} object, information
     * is stored informing the user of this Project of the occurrence.
     * @param concreteElementsMap The map containing all named concreteElements.
     * @param unsolvedReference The unsolved reference to solve.
     */
    private void replaceUnsolvedReference(Dictionary<String, List<NamedConcreteElement>> concreteElementsMap, UnsolvedReference unsolvedReference) {
        List<NamedConcreteElement> concreteElements = concreteElementsMap.get(unsolvedReference.getRef());

        if (concreteElements != null){
            Dictionary<String, String> oldElementAttributes = unsolvedReference.getElement().getAttributesMap();

            for (NamedConcreteElement concreteElement : concreteElements) {
                NamedConcreteElement substitutionElementWrapper;

                if (!unsolvedReference.isTypeRef()){
                    XsdNamedElements substitutionElement = concreteElement.getElement().clone(oldElementAttributes);

                    substitutionElementWrapper = (NamedConcreteElement) ReferenceBase.createFromXsd(substitutionElement);
                } else {
                    substitutionElementWrapper = concreteElement;
                }

                unsolvedReference.getParent().replaceUnsolvedElements(substitutionElementWrapper);
            }
        } else {
            storeUnsolvedItem(unsolvedReference);
        }
    }

    /**
     * Saves an occurrence of an element which couldn't be resolved in the {@link XsdParser#replaceUnsolvedReference}
     * method, which can be accessed at the end of the parsing process in order to verify if were there were any
     * references that couldn't be solved.
     * @param unsolvedReference The unsolved reference which couldn't be resolved.
     */
    private void storeUnsolvedItem(UnsolvedReference unsolvedReference) {
        if (parserUnsolvedElementsMap.isEmpty()){
            parserUnsolvedElementsMap.Add(new UnsolvedReferenceItem(unsolvedReference));
        } else {
            Optional<UnsolvedReferenceItem> innerEntry =
                    parserUnsolvedElementsMap.stream()
                            .filter(unsolvedReferenceObj ->
                                    unsolvedReferenceObj.getUnsolvedReference()
                                            .getRef()
                                            .equals(unsolvedReference.getRef()))
                            .findFirst();

            if (innerEntry.isPresent()){
                innerEntry.ifPresent(entry -> entry.getParents().Add(unsolvedReference.getParent()));
            } else {
                parserUnsolvedElementsMap.Add(new UnsolvedReferenceItem(unsolvedReference));
            }
        }
    }

    /**
     * @return The {@link List} of {@link UnsolvedReferenceItem} that represent all the objects with a reference that couldn't
     * be solved.
     */
    public List<UnsolvedReferenceItem> getUnsolvedReferences(){
        return parserUnsolvedElementsMap;
    }

    /**
     * @return A list of all the top level parsed xsd:elements by this class. It doesn't return any other elements apart
     * from xsd:elements. To access the whole element tree use {@link XsdParser#getResultXsdSchemas()}
     */
    public Stream<XsdElement> getResultXsdElements(){
        List<XsdElement> elements = new List<XsdElement>();

        getResultXsdSchemas().ForEach(schema -> schema.getChildrenElements().ForEach(elements::add));

        return elements.stream();
    }

    /**
     * @return A {@link List} of all the {@link XsdSchema} elements parsed by this class. You can use the {@link XsdSchema}
     * instances to navigate through the whole element tree.
     */
    public Stream<XsdSchema> getResultXsdSchemas(){
        return parseElements
                .values()
                .stream()
              //  #todo : check
                .flatmap()//(List::stream)
                .filter(element -> element.getElement() instanceof XsdSchema)
                .Select(element -> (XsdSchema) element.getElement());
    }

    /**
     * Adds an {@link UnsolvedReference} object to the {@link XsdParser#unsolvedElements} list which should be solved
     * at a later time in the parsing process.
     * @param unsolvedReference The unsolvedReference to add to the unsolvedElements list.
     */
    public void addUnsolvedReference(UnsolvedReference unsolvedReference){
        List<UnsolvedReference> unsolved = unsolvedElements.get(currentFile);

        if (unsolved == null){
            unsolved = new List<UnsolvedReference>();

            unsolvedElements.put(currentFile, unsolved);
        }

        unsolved.Add(unsolvedReference);
    }

    /**
     * Adds a new file to the parsing queue. This new file appears by having xsd:import or xsd:include tags in the
     * original file to parse.
     * @param schemaLocation A new file path of another XSD file to parse.
     */
    public void addFileToParse(String schemaLocation) {
        String fileName = schemaLocation.Substring(schemaLocation.LastIndexOf("/")+1);

        if (!schemaLocations.Contains(schemaLocation) && schemaLocation.EndsWith(".xsd") && schemaLocations.stream().noneMatch(sl -> sl.endsWith(fileName))){
            schemaLocations.Add(schemaLocation);
        }
    }

    public static Dictionary<String, String> getXsdTypesToJava() {
        return xsdTypesToJava;
    }

    public static Dictionary<String, BiFunction<XsdParserCore, XmlElement, ReferenceBase>> getParseMappers() {
        return parseMappers;
    }

    public void addParsedElement(ReferenceBase wrappedElement) {
        List<ReferenceBase> elements = parseElements.get(currentFile);

        if (elements == null){
            elements = new List<ReferenceBase>();

            parseElements.put(currentFile, elements);
        }

        elements.Add(wrappedElement);
    }

    void updateConfig(ParserConfig config) {
        xsdTypesToJava = config.getXsdTypesToJava();
        parseMappers = config.getParseMappers();
    }
}