namespace proholz.xsdparser;

type
  XsdParser = public class(XsdParserCore)
  private
    (**
     * This function uses DOM to obtain a list of nodes from a XSD file.
     * @param filePath The path to the XSD file.
     * @throws IOException If the file parsing throws {@link IOException}.

     * @throws ParserConfigurationException If the {@link DocumentBuilderFactory#newDocumentBuilder()} throws
     *      {@link ParserConfigurationException}.
     * @return A list of nodes that represent the node tree of the XSD file with the path received.
     *)
    method getSchemaNode(filePath : String) : XmlElement ;
    begin
      var doc := XmlDocument.TryFromFile(filePath);
      var node := doc.Root;//.Elements;
      if (isXsdSchema(node)) then exit node;
      raise new ParsingException("The top level element of a XSD file should be the xsd:schema node.");
    end;

    method parse(filePath : String);
    begin
      var basePath := Path.GetParentDirectory(filePath);
      schemaLocations.Add(Path.GetFileName(filePath));
      var index : Integer := 0;

      while (schemaLocations.Count > index) do begin
        var schemaLocation := schemaLocations.Item[index];
        parseFile(Path.Combine(basePath,  schemaLocation));
        inc(index);
      end;

      resolveRefs();
    end;

      /**
       * Parses a XSD file and all its containing XSD elements. This code iterates on the nodes and parses the supported
       * ones. The supported types are all the XSD types that have their tag present in the {@link XsdParser#parseMappers}
       * field.
       * @param filePath The path to the XSD file.
       */
    method parseFile(filePath : String);
    begin
       //https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
      self.currentFile := filePath;

      try
        if not File.Exists(filePath) then
          raise new FileNotFoundException();


        XsdSchema.parse(self, getSchemaNode(filePath));
      except
        on e : Exception do
         begin
          writeLn(e.Message);
       raise e;
       end;
      end;
    end;

  public
   (**
    * The XsdParser constructor will parse the XSD file with the {@code filepath} and will also parse all the subsequent
    * XSD files with their path present in xsd:import and xsd:include tags. After parsing all the XSD files present it
    * resolves the references existent in the XSD language, represented by the ref attribute. When this method finishes
    * the parse results and remaining unsolved references are accessible by the {@link XsdParser#getResultXsdSchemas()},
    * {@link XsdParser#getResultXsdElements()} and {@link XsdParser#getUnsolvedReferences()}.
    * @param filePath States the path of the XSD file to be parsed.
    *)
    constructor(filePath : String);
    begin
      parse(filePath);
    end;

     (**
       * The XsdParser constructor will parse the XSD file with the {@code filepath} and will also parse all the subsequent
       * XSD files with their path present in xsd:import and xsd:include tags. After parsing all the XSD files present it
       * resolves the references existent in the XSD language, represented by the ref attribute. When this method finishes
       * the parse results and remaining unsolved references are accessible by the {@link XsdParser#getResultXsdSchemas()},
       * {@link XsdParser#getResultXsdElements()} and {@link XsdParser#getUnsolvedReferences()}.
       * @param filePath States the path of the XSD file to be parsed.
       *)
    constructor (filePath : String; config : ParserConfig);
    begin
      inherited constructor();
      updateConfig(config);
      parse(filePath);
    end;
  end;


end.