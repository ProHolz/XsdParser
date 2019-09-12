package proholz.xsdparser;

/**
 * States the maximum length of a given type, either a {@link String}, a {@link List} or another measurable type.
 * This limit including the respective value. The value is defined as an {@link Integer}.
 * Example: If the type has a {@link XsdMaxLength#value} of 5 it means that the {@link String}, {@link List} or another
 * measurable type should have a maximum length of 5.
 */
public class XsdMaxLength extends XsdIntegerRestrictions {

	public static final String XSD_TAG = "xsd:maxLength";
	public static final String XS_TAG = "xs:maxLength";

	private XsdMaxLength(XsdParserCore! parser, Dictionary<String, String>! elementFieldsMapParam) {
		super(parser, elementFieldsMapParam);

		value = AttributeValidations.validateRequiredNonNegativeInteger(XSD_TAG, VALUE_TAG, attributesMap.Item[VALUE_TAG]);
	}

	@Override
	public void accept(XsdAbstractElementVisitor xsdAbstractElementVisitor) {
		super.accept(xsdAbstractElementVisitor);
		xsdAbstractElementVisitor.visit(this);
	}

	public static ReferenceBase parse(XsdParserCore! parser, XmlElement node){
		return ReferenceBase.createFromXsd(new XsdMaxLength(parser, convertNodeMap(node.Attributes)));
	}
}