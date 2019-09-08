package proholz.xsdparser;

/**
 * States a pattern that a given attribute must match in order to be considered valid. The value is defined as a
 * {@link String}. This value is usually defined as a regular expression.
 */
public class XsdPattern extends XsdStringRestrictions {

    public static final String XSD_TAG = "xsd:pattern";
    public static final String XS_TAG = "xs:pattern";

    private XsdPattern(XsdParserCore! parser, Dictionary<String, String>! elementFieldsMapParam) {
        super(parser, elementFieldsMapParam);
    }

    @Override
    public void accept(XsdAbstractElementVisitor xsdAbstractElementVisitor) {
        super.accept(xsdAbstractElementVisitor);
        xsdAbstractElementVisitor.visit(this);
    }

    public static ReferenceBase parse(XsdParserCore! parser, XmlElement node){
        return ReferenceBase.createFromXsd(new XsdPattern(parser, convertNodeMap(node.get_Attributes())));
    }
}