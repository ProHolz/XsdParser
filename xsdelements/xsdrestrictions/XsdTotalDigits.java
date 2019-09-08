package proholz.xsdparser;

/**
 * States the number of total digits allowed in a numeric type. The value is defined as an {@link Integer}.
 */
public class XsdTotalDigits extends XsdIntegerRestrictions {

    public static final String XSD_TAG = "xsd:totalDigits";
    public static final String XS_TAG = "xs:totalDigits";

    private XsdTotalDigits(@NotNull XsdParserCore parser, @NotNull Map<String, String> elementFieldsMapParam) {
        super(parser, elementFieldsMapParam);

        value = AttributeValidations.validateRequiredPositiveInteger(XSD_TAG, VALUE_TAG, attributesMap.get(VALUE_TAG));
    }

    @Override
    public void accept(XsdAbstractElementVisitor xsdAbstractElementVisitor) {
        super.accept(xsdAbstractElementVisitor);
        xsdAbstractElementVisitor.visit(this);
    }

    public static ReferenceBase parse(@NotNull XsdParserCore parser, Node node){
        return ReferenceBase.createFromXsd(new XsdTotalDigits(parser, convertNodeMap(node.getAttributes())));
    }
}
