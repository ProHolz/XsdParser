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

	private XsdMinLength(XsdParserCore! parser, Dictionary<String, String>! elementFieldsMapParam) {
		super(parser, elementFieldsMapParam);

		value = AttributeValidations.validateRequiredNonNegativeInteger(XSD_TAG, VALUE_TAG, attributesMap.Item[VALUE_TAG]);
	}

	@Override
	public void accept(XsdAbstractElementVisitor xsdAbstractElementVisitor) {
		super.accept(xsdAbstractElementVisitor);
		xsdAbstractElementVisitor.visit(this);
	}

	public static ReferenceBase parse(XsdParserCore! parser, XmlElement node){
		return ReferenceBase.createFromXsd(new XsdMinLength(parser, convertNodeMap(node.get_Attributes())));
	}
}