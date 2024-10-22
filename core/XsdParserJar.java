﻿package proholz.xsdparser;



public class XsdParserJar extends XsdParserCore {

    private static ClassLoader classLoader;

    /**
     * Adds the jar file represented the jarPath to the classpath and proceeds by parsing the file present in the
     * previous jar with the path filePath.
     * @param jarPath The path to the jar file.
     * @param filePath The filePath of the XSD file to parse. Relative to the Jar structure.
     */
    public XsdParserJar(String jarPath, String filePath){
        parse(jarPath, filePath);
    }

    /**
     * Adds the jar file represented the jarPath to the classpath and proceeds by parsing the file present in the
     * previous jar with the path filePath.
     * @param jarPath The path to the jar file.
     * @param filePath The filePath of the XSD file to parse. Relative to the Jar structure.
     */
    public XsdParserJar(String jarPath, String filePath, ParserConfig config){
        super.updateConfig(config);

        parse(jarPath, filePath);
    }

    private void parse(String jarPath, String filePath){
        setClassLoader(jarPath);

        parseJarFile(filePath);

        int index = 0;

        while (schemaLocations.Size() > index){
            String schemaLocation = schemaLocations.get(index);
            parseJarFile(schemaLocation);
            ++index;
        }

        resolveRefs();
    }

    /**
     * Parses the XSD file represented by the received InputStream.
     * @param filePath The filePath of the XSD file.
     */
    private void parseJarFile(String filePath) {
        //https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
        this.currentFile = filePath;
        InputStream inputStream = classLoader.getResourceAsStream(filePath);

        try {
            XmlElement schemaNode = getSchemaNode(inputStream);

            if (isXsdSchema(schemaNode)){
                XsdSchema.parse(this, schemaNode);
            } else {
                throw new ParsingException("The top level element of a XSD file should be the xsd:schema node.");
            }
        } catch (SAXException | IOException | ParserConfigurationException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Exception while parsing.", e);
        }
    }

    private XmlElement getSchemaNode(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);

        doc.getDocumentElement().normalize();

        return doc.getFirstChild();
    }

    /**
     * Creates a new class loader, replacing the current one, having another path added to the classpath. The new
     * path is the path to the jar received in this class constructor.
     * @param jarPath The path of the jar file.
     */
    private void setClassLoader(String jarPath) {
        if (!jarPath.EndsWith(".jar")){
            throw new ParsingException("The jarPath received doesn't represent a jar file.");
        }

        ClassLoader originalCl = Thread.CurrentThread().getContextClassLoader();

        URL url = originalCl.getResource(jarPath);

        if (url == null){
            try {
                url = new URL("file:/" + jarPath);
            } catch (MalformedURLException e) {
                throw new ParsingException("Invalid jar name.");
            }
        }

        // Create class loader using given codebase
        // Use prevCl as parent to maintain current visibility
        ClassLoader urlCl = URLClassLoader.newInstance(new URL[]{url}, originalCl);

        Thread.CurrentThread().setContextClassLoader(urlCl);

        classLoader = urlCl;
    }
}