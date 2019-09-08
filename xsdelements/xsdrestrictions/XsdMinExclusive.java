package proholz.xsdparser;

/**
 * States the minimum numeric value that a given type might take, excluding the respective value. The value is defined
 * as a {@link Double}.
 * Example: If the type has a {@link XsdMinExclusive#value} of 0 it means that the minimum value it can take is any
 * number bigger than 0.
 */
public class XsdMinExclusive extends XsdDoubleRestrictions {

    public static final String XSD_TAG = "xsd:minExclusive";
    public static final String XS_TAG = "xs:minExclusive";

    private XsdMinExclusive(@NotNull XsdParserCore parser, @NotNull Map<String, String> elementFieldsMapParam) {
        super(parser, elementFieldsMapParam, XSD_TAG);
    }

    @Override
    public void accept(XsdAbstractElementVisitor xsdAbstractElementVisitor) {
        super.accept(xsdAbstractElementVisitor);
        xsdAbstractElementVisitor.visit(this);
    }

    public static ReferenceBase parse(@NotNull XsdParserCore parser, Node node){
        return ReferenceBase.createFromXsd(new XsdMinExclusive(parser, convertNodeMap(node.getAttributes())));
    }
}
