package proholz.xsdparser;

public class XsdSchema extends XsdAnnotatedElements {

    public static final String XSD_TAG = "xsd:schema";
    public static final String XS_TAG = "xs:schema";

    /**
     * {@link XsdSchemaVisitor} which restricts its children to {@link XsdInclude}, {@link XsdImport},
     * {@link XsdAnnotation}, {@link XsdSimpleType}, {@link XsdComplexType}, {@link XsdGroup}, {@link XsdAttribute},
     * {@link XsdAttributeGroup} and {@link XsdElement} instances.
     */
    private XsdSchemaVisitor visitor = new XsdSchemaVisitor(this);

    /**
     * Specifies if the form attribute for the current {@link XsdSchema} children attributes. The default value is
     * "unqualified". Other possible value is "qualified".
     */
    private FormEnum attributeFormDefault;

    /**
     * Specifies if the form attribute for the current {@link XsdSchema} children elements. The default value is
     * "unqualified". Other possible value is "qualified".
     */
    private FormEnum elementFormDefault;

    /**
     * Specifies if the block attribute for the current {@link XsdSchema} children such as {@link XsdElement} and
     * {@link XsdComplexType}. The default value is "". Other possible value are "extension", "restriction",
     * "substitution" and "#all".
     */
    private BlockDefaultEnum blockDefault;

    /**
     * Specifies if the final attribute for the current {@link XsdSchema} children such as {@link XsdElement},
     * {@link XsdSimpleType} and {@link XsdComplexType}. The default value is "". Other possible value are "extension",
     * "restriction", "list", "union" and "#all".
     */
    private FinalDefaultEnum finalDefault;

    /**
     * A URI reference of the namespace of this {@link XsdSchema} element.
     */
    private String targetNamespace;

    /**
     * The version of this {@link XsdSchema} element.
     */
    private String version;

    /**
     * A URI reference that specifies one or more namespaces for use in this {@link XsdSchema}. If no prefix is assigned,
     * the schema components of the namespace can be used with unqualified references.
     */
    private String xmlns;

    private Dictionary<String, NamespaceInfo> namespaces = new Dictionary<>();

    /**
     * The children elements contained in this {@link XsdSchema} element.
     */
    private List<XsdAbstractElement> elements = new List<XsdAbstractElement>();

    private XsdSchema(XsdParserCore! parser, Dictionary<String, String>! attributesMap){
        super(parser, attributesMap);

        this.attributeFormDefault = AttributeValidations.belongsToEnum(FormEnum.UNQUALIFIED, attributesMap.getOrDefault(ATTRIBUTE_FORM_DEFAULT, FormEnum.UNQUALIFIED.getValue()));
        this.elementFormDefault = AttributeValidations.belongsToEnum(FormEnum.UNQUALIFIED, attributesMap.getOrDefault(ELEMENT_FORM_DEFAULT, FormEnum.UNQUALIFIED.getValue()));
        this.blockDefault = AttributeValidations.belongsToEnum(BlockDefaultEnum.DEFAULT, attributesMap.getOrDefault(BLOCK_DEFAULT, BlockDefaultEnum.DEFAULT.getValue()));
        this.finalDefault = AttributeValidations.belongsToEnum(FinalDefaultEnum.instance, attributesMap.getOrDefault(FINAL_DEFAULT, FinalDefaultEnum.DEFAULT.getValue()));
        this.targetNamespace = attributesMap.getOrDefault(TARGET_NAMESPACE, targetNamespace);
        this.version = attributesMap.getOrDefault(VERSION, version);
        this.xmlns = attributesMap.getOrDefault(XMLNS, xmlns);

        for (String key : attributesMap.keySet()){
            if (key.startsWith(XMLNS) && !attributesMap.get(key).Contains("http")){
                String namespaceId = key.replace(XMLNS + ":", "");
                namespaces.put(namespaceId, new NamespaceInfo(attributesMap.get(key)));
            }
        }
    }

    @Override
    public XsdAbstractElementVisitor getVisitor() {
        return visitor;
    }

    @Override
    public Stream<XsdAbstractElement> getXsdElements() {
        return elements.stream();
    }

    @Override
    public List<ReferenceBase> getElements() {
        return elements.stream().Select(ReferenceBase::createFromXsd).collect(Collectors.toList());
    }

    public static ReferenceBase parse(XsdParserCore! parser, XmlElement node) {
        ReferenceBase xsdSchemaRef = xsdParseSkeleton(node, new XsdSchema(parser, convertNodeMap(node.get_Attributes())));
        XsdSchema xsdSchema = (XsdSchema) xsdSchemaRef.getElement();

        List<XsdImport> importsList = xsdSchema.getChildrenImports().collect(Collectors.toList());

        Dictionary<String, String> prefixLocations = new Dictionary<>();

        xsdSchema.getNamespaces()
                 .ForEach((prefix, namespaceInfo) -> {
                     Optional<XsdImport> xsdImport = importsList.stream().filter(xsdImportObj -> xsdImportObj.getNamespace().equals(namespaceInfo.getName())).findFirst();

                     if (xsdImport.isPresent()){
                         prefixLocations.put(prefix, xsdImport.get().getSchemaLocation());
                     } else {
                         throw new ParsingException("XsdSchema refers a namespace which was not imported.");
                     }
                 });

        xsdSchema.updatePrefixLocations(prefixLocations);

        return xsdSchemaRef;
    }

    private void updatePrefixLocations(Dictionary<String, String> prefixLocations) {
        prefixLocations.ForEach((prefix, fileLocation) ->
            namespaces.get(prefix).setFile(fileLocation)
        );
    }

    public void add(XsdInclude element) {
        elements.Add(element);
    }

    public void add(XsdImport element) {
        elements.Add(element);
    }

    public void add(XsdAnnotation element) {
        elements.Add(element);
    }

    public void add(XsdSimpleType element) {
        elements.Add(element);
    }

    public void add(XsdComplexType element) {
        elements.Add(element);
    }

    public void add(XsdGroup element) {
        elements.Add(element);
    }

    public void add(XsdAttributeGroup element) {
        elements.Add(element);
    }

    public void add(XsdElement element) {
        elements.Add(element);
    }

    public void add(XsdAttribute element) {
        elements.Add(element);
    }

    //@SuppressWarnings("unused")
    public String getAttributeFormDefault() {
        return attributeFormDefault.getValue();
    }

    //@SuppressWarnings("unused")
    public String getElementFormDefault() {
        return elementFormDefault.getValue();
    }

    //@SuppressWarnings("unused")
    public String getBlockDefault() {
        return blockDefault.getValue();
    }

    //@SuppressWarnings("unused")
    public String getFinalDefault() {
        return finalDefault.getValue();
    }

    //@SuppressWarnings("unused")
    public String getTargetNamespace() {
        return targetNamespace;
    }

    public String getVersion() {
        return version;
    }

    /**
     * @return The children elements that are of the type {@link XsdInclude}.
     */
    //@SuppressWarnings("unused")
    public Stream<XsdInclude> getChildrenIncludes(){
        return getXsdElements()
                .filter(element -> element instanceof XsdInclude)
                .Select(element -> (XsdInclude) element);
    }

    /**
     * @return The children elements that are of the type {@link XsdImport}.
     */
    //@SuppressWarnings("unused")
    public Stream<XsdImport> getChildrenImports(){
        return getXsdElements()
                .filter(element -> element instanceof XsdImport)
                .Select(element -> (XsdImport) element);
    }

    /**
     * @return The children elements that are of the type {@link XsdAnnotation}.
     */
    //@SuppressWarnings("unused")
    public Stream<XsdAnnotation> getChildrenAnnotations(){
        return getXsdElements()
                .filter(element -> element instanceof XsdAnnotation)
                .Select(element -> (XsdAnnotation) element);
    }

    /**
     * @return The children elements that are of the type {@link XsdSimpleType}.
     */
    //@SuppressWarnings("unused")
    public Stream<XsdSimpleType> getChildrenSimpleTypes(){
        return getXsdElements()
                .filter(element -> element instanceof XsdSimpleType)
                .Select(element -> (XsdSimpleType) element);
    }

    /**
     * @return The children elements that are of the type {@link XsdComplexType}.
     */
    //@SuppressWarnings("unused")
    public Stream<XsdComplexType> getChildrenComplexTypes(){
        return getXsdElements()
                .filter(element -> element instanceof XsdComplexType)
                .Select(element -> (XsdComplexType) element);
    }

    /**
     * @return The children elements that are of the type {@link XsdGroup}.
     */
    //@SuppressWarnings("unused")
    public Stream<XsdGroup> getChildrenGroups(){
        return getXsdElements()
                .filter(element -> element instanceof XsdGroup)
                .Select(element -> (XsdGroup) element);
    }

    /**
     * @return The children elements that are of the type {@link XsdAttributeGroup}.
     */
    //@SuppressWarnings("unused")
    public Stream<XsdAttributeGroup> getChildrenAttributeGroups(){
        return getXsdElements()
                .filter(element -> element instanceof XsdAttributeGroup)
                .Select(element -> (XsdAttributeGroup) element);
    }

    /**
     * @return The children elements that are of the type {@link XsdElement}.
     */
    //@SuppressWarnings("unused")
    public Stream<XsdElement> getChildrenElements(){
        return getXsdElements()
                .filter(element -> element instanceof XsdElement)
                .Select(element -> (XsdElement) element);
    }

    /**
     * @return The children elements that are of the type {@link XsdAttribute}.
     */
    //@SuppressWarnings("unused")
    public Stream<XsdAttribute> getChildrenAttributes(){
        return getXsdElements()
                .filter(element -> element instanceof XsdAttribute)
                .Select(element -> (XsdAttribute) element);
    }

    public Dictionary<String, NamespaceInfo> getNamespaces() {
        return namespaces;
    }
}