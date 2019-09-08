package proholz.xsdparser;

/**
 * States the exact number of length to a given type, either a {@link String}, a {@link List}, or another measurable type.
 * The value is defined as an {@link Integer}.
 */
public class XsdLength extends XsdIntegerRestrictions {

    public static final String XSD_TAG = "xsd:length";
    public static final String XS_TAG = "xs:length";

    private XsdLength(@NotNull XsdParserCore parser, @NotNull Map<String, String> elementFieldsMapParam) {
        super(parser, elementFieldsMapParam);

        value = AttributeValidations.validateRequiredNonNegativeInteger(XSD_TAG, VALUE_TAG, attributesMap.get(VALUE_TAG));
    }

    @Override
    public void accept(XsdAbstractElementVisitor xsdAbstractElementVisitor) {
        super.accept(xsdAbstractElementVisitor);
        xsdAbstractElementVisitor.visit(this);
    }

    public static ReferenceBase parse(@NotNull XsdParserCore parser, Node node){
        return ReferenceBase.createFromXsd(new XsdLength(parser, convertNodeMap(node.getAttributes())));
    }
}
