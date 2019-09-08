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
    private List<XsdAppInfo> appInfoList = new ArrayList<>();

    /**
     * The list of {@link XsdDocumentation} children.
     */
    private List<XsdDocumentation> documentations = new ArrayList<>();

    private XsdAnnotation(@NotNull XsdParserCore parser, @NotNull Map<String, String> elementFieldsMapParam) {
        super(parser, elementFieldsMapParam);
    }

    @Override
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
        appInfoList.add(appInfo);
    }

    public void add(XsdDocumentation documentation){
        documentations.add(documentation);
    }

    public static ReferenceBase parse(@NotNull XsdParserCore parser, Node node){
        return xsdParseSkeleton(node, new XsdAnnotation(parser, convertNodeMap(node.getAttributes())));
    }
}
