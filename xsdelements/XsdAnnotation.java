package proholz.xsdparser;

/**
 * A class representing the xsd:annotation element.
 *
 * @see <a href="https://www.w3schools.com/xml/el_annotation.asp">xsd:annotation element description and usage at w3c</a>
 */
public class XsdAnnotation extends XsdIdentifierElements {

    public static final String XSD_TAG = "xsd:annotation";
    public static final String XS_TAG = "xs:annotation";

    /**
     * {@link XsdAnnotationVisitor} instance which limits its children types to {@link XsdAppInfo} and
     * {@link XsdDocumentation} instances.
     */
    private XsdAnnotationVisitor visitor = new XsdAnnotationVisitor(this);

    /**
     * The list of {@link XsdAppInfo} children.
     */
    private List<XsdAppInfo> appInfoList = new List<XsdAppInfo>();

    /**
     * The list of {@link XsdDocumentation} children.
     */
    private List<XsdDocumentation> documentations = new List<XsdDocumentation>();

    private XsdAnnotation( XsdParserCore! parser,  Dictionary<String, String>! elementFieldsMapParam) {
        super(parser, elementFieldsMapParam);
    }

 //   @Override
    public XsdAnnotationVisitor getVisitor() {
        return visitor;
    }

    @Override
    public void accept(XsdAbstractElementVisitor visitorParam) {
        super.accept(visitorParam);
        visitorParam.visit(this);
    }

    public List<XsdAppInfo> getAppInfoList() {
        return appInfoList;
    }

    public List<XsdDocumentation> getDocumentations() {
        return documentations;
    }

    public void add(XsdAppInfo appInfo){
        appInfoList.Add(appInfo);
    }

    public void add(XsdDocumentation documentation){
        documentations.Add(documentation);
    }

    public static ReferenceBase parse( XsdParserCore! parser, XmlElement node){
        return xsdParseSkeleton(node, new XsdAnnotation(parser, convertNodeMap(node.get_Attributes())));
    }
}