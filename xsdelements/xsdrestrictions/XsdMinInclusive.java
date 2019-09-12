package proholz.xsdparser;

/**
 * States the minimum numeric value that a given type might take, including the respective value. The value is defined
 * as a {@link Double}.
 * Example: If the type has a {@link XsdMinInclusive} of 0 it means that the minimum value it can take is 0.
 */
public class XsdMinInclusive extends XsdDoubleRestrictions {

	public static final String XSD_TAG = "xsd:minInclusive";
	public static final String XS_TAG = "xs:minInclusive";

	private XsdMinInclusive(XsdParserCore! parser, Dictionary<String, String>! elementFieldsMapParam) {
		super(parser, elementFieldsMapParam, XSD_TAG);
	}

	@Override
	public void accept(XsdAbstractElementVisitor xsdAbstractElementVisitor) {
		super.accept(xsdAbstractElementVisitor);
		xsdAbstractElementVisitor.visit(this);
	}

	public static ReferenceBase parse(XsdParserCore! parser, XmlElement node){
		return ReferenceBase.createFromXsd(new XsdMinInclusive(parser, convertNodeMap(node.Attributes)));
	}
}