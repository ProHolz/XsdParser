package proholz.xsdparser;

/**
 * States the maximum numeric value that a given type might take, excluding the respective value. The value is defined
 * as a {@link Double}.
 * Example: If the numeric type has a {@link XsdMaxExclusive#value} of 5 it means that the maximum value it can take is
 * 4.9999(9).
 */
public class XsdMaxExclusive extends XsdDoubleRestrictions {

    public static final String XSD_TAG = "xsd:maxExclusive";
    public static final String XS_TAG = "xs:maxExclusive";

    private XsdMaxExclusive(@NotNull XsdParserCore parser, @NotNull Map<String, String> elementFieldsMapParam) {
        super(parser, elementFieldsMapParam, XSD_TAG);
    }

    @Override
    public void accept(XsdAbstractElementVisitor xsdAbstractElementVisitor) {
        super.accept(xsdAbstractElementVisitor);
        xsdAbstractElementVisitor.visit(this);
    }

    public static ReferenceBase parse(@NotNull XsdParserCore parser, Node node){
        return ReferenceBase.createFromXsd(new XsdMaxExclusive(parser, convertNodeMap(node.getAttributes())));
    }
}
