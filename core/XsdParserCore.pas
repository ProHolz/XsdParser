﻿namespace proholz.xsdparser;
interface

type
  XsdParserCore = public class

  protected
//
//      * A object that contains a parse function to each {@link XsdAbstractElement} concrete
//      * type supported by this mapper, this way based on the concrete {@link XsdAbstractElement} tag the according parse
//      * method can be invoked.
//
//
    class var parseMappers: Dictionary<String,BiFunction<XsdParserCore,XmlElement,ReferenceBase>>;
    // *
    //      * A {@link Map} object that contains the all the XSD types and their respective types in the Java
    //      * language.
    //
    //
    class var xsdTypesToCodeGen: Dictionary<String,String>;
    // *
    //      * A {@link List} which contains all the top elements parsed by this class.
    //
    //
    var parseElements:= new Dictionary<String,List<ReferenceBase>>();
    // *
    //      * A {@link List} of {@link UnsolvedReference} elements that weren't solved. This list is consulted after all the
    //      * elements are parsed in order to find if there is any suitable parsed element to replace the unsolved element.
    //
    //
    var unsolvedElements:= new Dictionary<String,List<UnsolvedReference>>();// := new HashMap();
    // *
    //      * A {@link List} containing all the elements that even after parsing all the elements on the file, don't have a
    //      * suitable object to replace the reference. This list can be consulted after the parsing process to assert if there
    //      * is any missing information in the XSD file.
    //
    //
    var parserUnsolvedElementsMap:= new List<UnsolvedReferenceItem>();// := new ArrayList();
    // *
    //      * A {@link List} containing the paths of files that were present in either {@link XsdInclude} or {@link XsdImport}
    //      * objects that are present in the original or subsequent files. These paths are stored to be parsed as well, the
    //      * parsing process only ends when all the files present in this {@link List} are parsed.
    //
    //
    var schemaLocations:= new List<String>();// := new ArrayList();
    var currentFile: String;

    class constructor;


  // *
  //      * Verifies if a given {@link Node} object, i.e. {@code node} is a xsd:schema node.
  //      * @param node The node to verify.
  //      * @return True if the node is a xsd:schema or xs:schema. False otherwise.
  //
    method isXsdSchema(node: XmlElement): Boolean;


// *
//      * This method resolves all the remaining {@link UnsolvedReference} objects present after all the elements are parsed.
//      * It starts by iterating all {@link XsdParser#parseElements} and inserting all the parsed elements with a name
//      * attribute in the concreteElementsMap variable. After that it iterates on the {@link XsdParser#unsolvedElements}
//      * list in order to find if any of the unsolvedReferences can be solved by replacing the unsolvedElement by its
//      * matching {@link NamedConcreteElement} object, present in the concreteElementsMap. The {@link UnsolvedReference}
//      * objects matches a {@link NamedConcreteElement} object by having its ref attribute with the same value as the
//      * name attribute of the {@link NamedConcreteElement}.
//
    method resolveRefs;
    begin
      resolveInnerRefs();
      resolveOtherNamespaceRefs();
    end;

    method resolveOtherNamespaceRefs;

    method replaceUnsolvedImportedReference(concreteElementsMap: Dictionary<String,List<NamedConcreteElement>>; unsolvedReference: UnsolvedReference);

    method resolveInnerRefs;


 // *
 //      * Replaces a single {@link UnsolvedReference} object, with the respective {@link NamedConcreteElement} object. If
 //      * there isn't a {@link NamedConcreteElement} object to replace the {@link UnsolvedReference} object, information
 //      * is stored informing the user of this Project of the occurrence.
 //      * @param concreteElementsMap The map containing all named concreteElements.
 //      * @param unsolvedReference The unsolved reference to solve.
 //
    method replaceUnsolvedReference(concreteElementsMap: Dictionary<String,List<NamedConcreteElement>>; unsolvedReference: UnsolvedReference);

 // *
 //      * Saves an occurrence of an element which couldn't be resolved in the {@link XsdParser#replaceUnsolvedReference}
 //      * method, which can be accessed at the end of the parsing process in order to verify if were there were any
 //      * references that couldn't be solved.
 //      * @param unsolvedReference The unsolved reference which couldn't be resolved.
 //
    method storeUnsolvedItem(unsolvedReference: UnsolvedReference);

    method updateConfig(config: ParserConfig);


  public
    class method getXsdTypesToCodeGen: Dictionary<String,String>;
    class method getParseMappers: Dictionary<String,BiFunction<XsdParserCore, XmlElement,ReferenceBase>>;
    method addParsedElement(wrappedElement: ReferenceBase);
     // *
 //      * Adds an UnsolvedReference object to the unsolvedElements list which should be solved
 //      * at a later time in the parsing process.
 //      * @param unsolvedReference The unsolvedReference to add to the unsolvedElements list.
 //
    method addUnsolvedReference(unsolvedReference: UnsolvedReference);
    // *
//      * Adds a new file to the parsing queue. This new file appears by having xsd:import or xsd:include tags in the
//      * original file to parse.
//      * @param schemaLocation A new file path of another XSD file to parse.
//
    method addFileToParse(schemaLocation: String);

 // *
//      * @return The {@link List} of {@link UnsolvedReferenceItem} that represent all the objects with a reference that couldn't
//      * be solved.
//
    method getUnsolvedReferences: List<UnsolvedReferenceItem>;

    (**
     * @return A list of all the top level parsed xsd:elements by this class. It doesn't return any other elements apart
     * from xsd:elements. To access the whole element tree use {@link XsdParser#getResultXsdSchemas()}
     *)
    method getResultXsdElements() : List<XsdElement>;

      (**
       * @return A {@link List} of all the {@link XsdSchema} elements parsed by this class. You can use the {@link XsdSchema}
       * instances to navigate through the whole element tree.
       *)

    method  getResultXsdSchemas() : List<XsdSchema>;

    method getCurrentFile : String;


  end;
implementation

class constructor XsdParserCore;
begin
  var config := new DefaultParserConfig();
  parseMappers := config.getParseMappers();
  xsdTypesToCodeGen := config.getXsdTypesToCodeGen();
end;

method XsdParserCore.updateConfig(config: ParserConfig);
begin
  xsdTypesToCodeGen := config.getXsdTypesToCodeGen();
  parseMappers := config.getParseMappers();
end;

method XsdParserCore.addParsedElement(wrappedElement: ReferenceBase);
begin
  var parsedfilename := Path.GetFileName(currentFile);
  var elements := parseElements[parsedfilename];
  if not assigned(elements) then begin
    elements := new List<ReferenceBase>();

    parseElements.Add(parsedfilename, elements);
  end;
  elements.add(wrappedElement);

end;

class method XsdParserCore.getParseMappers: Dictionary<String,BiFunction<XsdParserCore,XmlElement,ReferenceBase>>;
begin
  exit parseMappers;
end;

class method XsdParserCore.getXsdTypesToCodeGen: Dictionary<String,String>;
begin
  exit xsdTypesToCodeGen;
end;

method XsdParserCore.addFileToParse(schemaLocation: String);
begin
  // Should use Path functions?
  var fileName: String := schemaLocation.substring(schemaLocation.lastIndexOf('/') + 1);
  if not schemaLocations.Contains(fileName)
    and schemaLocation.endsWith('.xsd')
    and schemaLocations
    .Any((sl) -> not sl.endsWith(fileName)) then begin
    schemaLocations.add(fileName);
  end;

end;

method XsdParserCore.addUnsolvedReference(unsolvedReference: UnsolvedReference);
begin
  var tempname := Path.GetFileName(currentFile);
  var unsolved:= unsolvedElements[tempname];
  if not assigned(unsolved) then begin
    unsolved := new List<UnsolvedReference>();
    unsolvedElements.Add(tempname, unsolved);
  end;
  unsolved.add(unsolvedReference);
end;

method XsdParserCore.getResultXsdSchemas: List<XsdSchema>;
begin
  result := new List<XsdSchema>();
  Var l1 := parseElements.Values;
  // Simulates flatMap from Java
  for each  element  in l1 do begin
    for each  ele  in element do
      if ele.getElement is XsdSchema  then
        result.Add(ele.getElement as XsdSchema);
  end;

end;

method XsdParserCore.getResultXsdElements: List<XsdElement>;
begin
  var elements := new List<XsdElement>();//  = new ArrayList<>();
  for each schema  in getResultXsdSchemas do
    begin
    for each child in schema.getChildrenElements() do
      elements.Add(child);
  end;
  exit elements;
end;

method XsdParserCore.getUnsolvedReferences: List<UnsolvedReferenceItem>;
begin
  exit parserUnsolvedElementsMap;
end;

method XsdParserCore.storeUnsolvedItem(unsolvedReference: UnsolvedReference);
begin
  if parserUnsolvedElementsMap.isEmpty() then begin
    parserUnsolvedElementsMap.add(new UnsolvedReferenceItem(unsolvedReference));
  end
  else begin
    var innerEntry: UnsolvedReferenceItem :=
     parserUnsolvedElementsMap
     //.stream()
     .Where(unsolvedReferenceObj -> unsolvedReferenceObj.getUnsolvedReference().getRef().equals(unsolvedReference.getRef()))
     .FirstOrDefault();
    if assigned(innerEntry) then begin
      innerEntry.getParents().Add(unsolvedReference.getParent());
    end
    else begin
      parserUnsolvedElementsMap.add(new UnsolvedReferenceItem(unsolvedReference));
    end;
  end;

end;

method XsdParserCore.replaceUnsolvedReference(concreteElementsMap: Dictionary<String,List<NamedConcreteElement>>; unsolvedReference: UnsolvedReference);
begin
  var concreteElements := concreteElementsMap[unsolvedReference.getRef()];
  if (concreteElements <> nil) then begin
    var oldElementAttributes  := unsolvedReference.getElement().getAttributesMap();
    for each matching concreteElement : NamedConcreteElement in concreteElements do begin
      var substitutionElementWrapper: NamedConcreteElement;
      if not unsolvedReference.isTypeRef() then begin
      // Should be save because of NamedConcretElement
      // Maybe we can add a save Property but......
        Var temp := concreteElement.getElement() as XsdNamedElements;
        var substitutionElement := temp.clone(oldElementAttributes);
        substitutionElementWrapper := NamedConcreteElement(ReferenceBase.createFromXsd(substitutionElement));
      end
      else begin
        substitutionElementWrapper := concreteElement;
      end;
      unsolvedReference.getParent().replaceUnsolvedElements(substitutionElementWrapper);
    end;
  end
  else begin
    storeUnsolvedItem(unsolvedReference);
  end;

end;

method XsdParserCore.resolveInnerRefs;
begin
  for each filename  in parseElements.Keys do begin
    var includedFiles := new List<String>();
    var pa1 := parseElements[filename];
    if assigned(pa1) then begin
      var LL1 :=
      pa1
      .Where(ref -> (ref is  ConcreteElement)).
      Select(ref -> ref as ConcreteElement);

      for each  ele  in LL1 do begin
        if ele.getElement is XsdInclude then begin
          var temp := XsdInclude(ele.getElement()).getSchemaLocation();
          includedFiles.Add(temp);
        end;
      end;
    end; // Pa1
    var includedElements := new List<ReferenceBase>(parseElements[filename]);

   // (parseElements[filename]);

    includedFiles
     .ForEach(item -> begin
       var temp := parseElements[item];
       if assigned(temp) then
         includedElements.Add(temp);

     end);
    var concreteElementsMap :=
    includedElements
    .Where(item -> item is NamedConcreteElement)
   .Select(item ->  item as NamedConcreteElement)
   .GroupBy(item -> item.getName)
   .ToDictionary(item -> item.Key, item -> item.ToList());

    var theunsolvedBase :=
     unsolvedElements.getOrDefault(filename, new List<UnsolvedReference>());
    var theunsolved :=
     theunsolvedBase
     .Where(unsolvedElement -> begin
       var theref := unsolvedElement.getRef();
       result := not theref.contains(":")
     end
     )
   .ToList();

    theunsolved
    .ForEach(unsolvedElement ->
     begin
       replaceUnsolvedReference(concreteElementsMap, unsolvedElement)
      end
    );

  end; // Filename in Scope
end;

method XsdParserCore.replaceUnsolvedImportedReference(concreteElementsMap: Dictionary<String,List<NamedConcreteElement>>; unsolvedReference: UnsolvedReference);
begin
  // List<NamedConcreteElement>
  var concreteElements := concreteElementsMap[unsolvedReference.getRef().substring(unsolvedReference.getRef().indexOf(':') + 1)];

  if assigned(concreteElements) then begin
    var oldElementAttributes:= unsolvedReference.getElement().getAttributesMap();
    for each concreteElement: NamedConcreteElement in concreteElements do begin
      var substitutionElementWrapper: NamedConcreteElement;
      if not unsolvedReference.isTypeRef() then begin
        if  (concreteElement.getElement() is  XsdNamedElements) then begin
          var substitutionElement := XsdNamedElements(concreteElement.getElement()).clone(oldElementAttributes);
          substitutionElementWrapper := NamedConcreteElement(ReferenceBase.createFromXsd(substitutionElement));
        end;
      end
      else begin
        substitutionElementWrapper := concreteElement;
      end;
      unsolvedReference.getParent().replaceUnsolvedElements(substitutionElementWrapper);
    end;
  end
  else begin
    storeUnsolvedItem(unsolvedReference);
  end;

end;

method XsdParserCore.resolveOtherNamespaceRefs;
begin

  parseElements
           .Keys
           .ForEach(fileName ->
           begin
             var lxsdSchema : XsdSchema  :=
                       parseElements[fileName]
                               //.stream()
                               .Where(ref -> begin
                                 exit (ref is ConcreteElement) and (ref.getElement() is XsdSchema);
                               end)
                 .Select(ref -> (ref.getElement() as XsdSchema))
                 .FirstorDefault();


             var ns := lxsdSchema.getNamespaces();

             unsolvedElements
                     .getOrDefault(fileName, new List<UnsolvedReference>())
                     //.stream()
                     .Where(unsolvedElement -> unsolvedElement.getRef().contains(":"))
                     .ToList()
                     .ForEach(unsolvedElement -> begin
                       var unsolvedElementNamespace := unsolvedElement.getRef().substring(0, unsolvedElement.getRef().indexOf(":"));

                       var foundNamespaceId :=
                                ns.Keys
                              // .stream()
                               .Where(namespaceId -> namespaceId.equals(unsolvedElementNamespace)).FirstOrDefault;

                       if assigned(foundNamespaceId) then begin
                         var importedFileLocation := ns[foundNamespaceId].getFile();

                         var importedFileName := importedFileLocation.substring(importedFileLocation.lastIndexOf("/")+1);

                         var importedElements  :=
                           parseElements[importedFileLocation];

                         if importedElements = nil then
                         begin
                           var lTemp := parseElements.Keys
                                         .FirstOrDefault(k -> k.endsWith(importedFileName));

                           if assigned(lTemp) then
                             importedElements  :=
                             parseElements[lTemp];

                         end;

                         if assigned(importedElements) then begin
                           var concreteElementsMap :=
                                   importedElements
                                           .Where(concreteEle -> concreteEle is NamedConcreteElement)
                                           .Select(concreteEle ->  concreteEle as NamedConcreteElement)
                                           .groupBy(Named -> Named.getName)
                                           .ToDictionary(item -> item.Key, item -> item.ToList());;

                           replaceUnsolvedImportedReference(concreteElementsMap, unsolvedElement);

                         end;
                       end;
                     end);
           end);


end;

method XsdParserCore.isXsdSchema(node: XmlElement): Boolean;
begin
  var schemaNodeName: String := node.FullName;
  exit schemaNodeName.equals(XsdSchema.XSD_TAG) or schemaNodeName.equals(XsdSchema.XS_TAG);
end;

method XsdParserCore.getCurrentFile: String;
begin
  exit currentFile;
end;


end.