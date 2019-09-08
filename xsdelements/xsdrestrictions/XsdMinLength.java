package proholz.xsdparser;

/**
 * States the minimum length of a given type, either a {@link String}, a {@link List} or another measurable type. This
 * limit includes the respective value. The value is defined as an {@link Integer}.
 * Example: If the type has a {@link XsdMinLength#value} of 2 it means that the {@link String}, {@link List} or another
 * measurable type should have a minimum length of 2.
 */
public class XsdMinLength extends XsdIntegerRestrictions {

    public static final String XSD_TAG = "xsd:minLength";
    public static final String XS_TAG = "xs:minLength";

    private XsdMinLength(@NotNull XsdParserCore parser, @NotNull Map<String, String> elementFieldsMapParam) {
        super(parser, elementFieldsMapParam);

        value = AttributeValidations.validateRequiredNonNegativeInteger(XSD_TAG, VALUE_TAG, attributesMap.get(VALUE_TAG));
    }

    @Override
    public void accept(XsdAbstractElementVisitor xsdAbstractElementVisitor) {
        super.accept(xsdAbstractElementVisitor);
        xsdAbstractElementVisitor.visit(this);
    }

    public static ReferenceBase parse(@NotNull XsdParserCore parser, Node node){
        return ReferenceBase.createFromXsd(new XsdMinLength(parser, convertNodeMap(node.getAttributes())));
    }
}
