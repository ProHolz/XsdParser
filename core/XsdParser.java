﻿package proholz.xsdparser;


/**
 * {@link XsdParser} in the core class of the XsdParser project. It functions as a one shot class, receiving the name
 * of the file to parse in its constructor and storing the parse results in its multiple fields, which can be consulted
 * after the instance is created.
 */
public class XsdParser extends XsdParserCore{

    /**
     * The XsdParser constructor will parse the XSD file with the {@code filepath} and will also parse all the subsequent
     * XSD files with their path present in xsd:import and xsd:include tags. After parsing all the XSD files present it
     * resolves the references existent in the XSD language, represented by the ref attribute. When this method finishes
     * the parse results and remaining unsolved references are accessible by the {@link XsdParser#getResultXsdSchemas()},
     * {@link XsdParser#getResultXsdElements()} and {@link XsdParser#getUnsolvedReferences()}.
     * @param filePath States the path of the XSD file to be parsed.
     */
    public XsdParser(String filePath){
        parse(filePath);
    }

    /**
     * The XsdParser constructor will parse the XSD file with the {@code filepath} and will also parse all the subsequent
     * XSD files with their path present in xsd:import and xsd:include tags. After parsing all the XSD files present it
     * resolves the references existent in the XSD language, represented by the ref attribute. When this method finishes
     * the parse results and remaining unsolved references are accessible by the {@link XsdParser#getResultXsdSchemas()},
     * {@link XsdParser#getResultXsdElements()} and {@link XsdParser#getUnsolvedReferences()}.
     * @param filePath States the path of the XSD file to be parsed.
     */
    public XsdParser(String filePath, ParserConfig config){
        super.updateConfig(config);

        parse(filePath);
    }

    private void parse(String filePath){
        schemaLocations.Add(filePath);
        int index = 0;

        while (schemaLocations.Count > index){
            String schemaLocation = schemaLocations.Item[index];
            parseFile(schemaLocation);
            ++index;
        }

        resolveRefs();
    }

    /**
     * Parses a XSD file and all its containing XSD elements. This code iterates on the nodes and parses the supported
     * ones. The supported types are all the XSD types that have their tag present in the {@link XsdParser#parseMappers}
     * field.
     * @param filePath The path to the XSD file.
     */
    private void parseFile(String filePath) {
        //https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
        this.currentFile = filePath;

        try {
            if (!new File(filePath).Exists()){
                throw new FileNotFoundException();
            }

            XsdSchema.parse(this, getSchemaNode(filePath));
        } catch (SAXException | IOException | ParserConfigurationException e) {
          //  Logger.getAnonymousLogger().log(Level.SEVERE, "Exception while parsing.", e);
        }
    }

    /**
     * This function uses DOM to obtain a list of nodes from a XSD file.
     * @param filePath The path to the XSD file.
     * @throws IOException If the file parsing throws {@link IOException}.

     * @throws ParserConfigurationException If the {@link DocumentBuilderFactory#newDocumentBuilder()} throws
     *      {@link ParserConfigurationException}.
     * @return A list of nodes that represent the node tree of the XSD file with the path received.
     */
    private XmlElement getSchemaNode(String filePath) throws IOException,  ParserConfigurationException {

        var doc = XmlDocument.TryFromFile(filePath);

        //ImmutableList<XmlElement>
        var node = doc.Root;//.Elements;

       // for (int i = 0; i < nodes.Count(); i++) {
         //   var node = nodes.ElementAt(i);
            if (isXsdSchema(node)){
                return node;
            }
       // }

        throw new ParsingException("The top level element of a XSD file should be the xsd:schema node.");
    }
}