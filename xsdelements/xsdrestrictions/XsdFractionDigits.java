package proholz.xsdparser;
/**
 * States the number of fraction digits allowed in a numeric type. The value is defined as an {@link Integer}.
 */
public class XsdFractionDigits extends XsdIntegerRestrictions {

    public static final String XSD_TAG = "xsd:fractionDigits";
    public static final String XS_TAG = "xs:fractionDigits";

    private XsdFractionDigits(@NotNull XsdParserCore parser, @NotNull Map<String, String> elementFieldsMapParam) {
        super(parser, elementFieldsMapParam);

        value = AttributeValidations.validateRequiredNonNegativeInteger(XSD_TAG, VALUE_TAG, attributesMap.get(VALUE_TAG));
    }

    @Override
    public void accept(XsdAbstractElementVisitor xsdAbstractElementVisitor) {
        super.accept(xsdAbstractElementVisitor);
        xsdAbstractElementVisitor.visit(this);
    }

    public static ReferenceBase parse(@NotNull XsdParserCore parser, Node node){
        return ReferenceBase.createFromXsd(new XsdFractionDigits(parser, convertNodeMap(node.getAttributes())));
    }
}
