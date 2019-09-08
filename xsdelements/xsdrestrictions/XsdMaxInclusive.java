package proholz.xsdparser;

/**
 * States the maximum numeric value that a given type might take, including the respective value. The value is defined
 * as a {@link Double}.
 * Example: If the numeric type has a {@link XsdMaxInclusive#value} of 5 it means that the maximum value it can take is 5.
 */
public class XsdMaxInclusive extends XsdDoubleRestrictions {

    public static final String XSD_TAG = "xsd:maxInclusive";
    public static final String XS_TAG = "xs:maxInclusive";

    private XsdMaxInclusive(XsdParserCore! parser, Dictionary<String, String>! elementFieldsMapParam) {
        super(parser, elementFieldsMapParam, XSD_TAG);
    }

    @Override
    public void accept(XsdAbstractElementVisitor xsdAbstractElementVisitor) {
        super.accept(xsdAbstractElementVisitor);
        xsdAbstractElementVisitor.visit(this);
    }

    public static ReferenceBase parse(XsdParserCore! parser, XmlElement node){
        return ReferenceBase.createFromXsd(new XsdMaxInclusive(parser, convertNodeMap(node.get_Attributes())));
    }
}