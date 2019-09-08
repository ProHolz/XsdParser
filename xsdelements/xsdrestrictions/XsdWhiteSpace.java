package proholz.xsdparser;

/**
 * States how the whiteSpace characters should be treated. The value is defined as an {@link String}.
 */
public class XsdWhiteSpace extends XsdAnnotatedElements {

    public static final String XSD_TAG = "xsd:whiteSpace";
    public static final String XS_TAG = "xs:whiteSpace";

    private XsdAnnotatedElementsVisitor visitor = new XsdAnnotatedElementsVisitor(this);

    private boolean fixed;
    private WhiteSpaceEnum value;

    private XsdWhiteSpace(XsdParserCore! parser, Dictionary<String, String>! elementFieldsMapParam) {
        super(parser, elementFieldsMapParam);

        fixed = AttributeValidations.validateBoolean(attributesMap.getOrDefault(FIXED_TAG, "false"));
        value = AttributeValidations.belongsToEnum(WhiteSpaceEnum.preserve, elementFieldsMapParam.getOrDefault(VALUE_TAG, null));
    }

    @Override
    public void accept(XsdAbstractElementVisitor xsdAbstractElementVisitor) {
        super.accept(xsdAbstractElementVisitor);
        xsdAbstractElementVisitor.visit(this);
    }

    public static ReferenceBase parse(XsdParserCore! parser, XmlElement node){
        return ReferenceBase.createFromXsd(new XsdWhiteSpace(parser, convertNodeMap(node.get_Attributes())));
    }

    public boolean isFixed() {
        return fixed;
    }

   // @Override
    public XsdAnnotatedElementsVisitor getVisitor() {
        return visitor;
    }

    public WhiteSpaceEnum getValue() {
        return value;
    }

    public static boolean hasDifferentValue(XsdWhiteSpace o1, XsdWhiteSpace o2) {
        if (o1 == null && o2 == null) {
            return false;
        }

        WhiteSpaceEnum o1Value;
        WhiteSpaceEnum o2Value;

        if (o1 != null) {
            o1Value = o1.getValue();
        }

        if (o2 != null) {
            o2Value = o2.getValue();
            return o2Value.Equals(o1Value);
        }

        return false;
    }
}