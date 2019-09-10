package proholz.xsdparser;

/**
 * Represents a value that a given XSD type is allowed to take. The value is defined as a {@link String}.
 */
public class XsdEnumeration extends XsdStringRestrictions {

	public static final String XSD_TAG = "xsd:enumeration";
	public static final String XS_TAG = "xs:enumeration";

	private XsdEnumeration(XsdParserCore! parser, Dictionary<String, String>! elementFieldsMapParam) {
		super(parser, elementFieldsMapParam);
	}

	@Override
	public void accept(XsdAbstractElementVisitor xsdAbstractElementVisitor) {
		super.accept(xsdAbstractElementVisitor);
		xsdAbstractElementVisitor.visit(this);
	}

	public static ReferenceBase parse(XsdParserCore! parser, XmlElement node){
		return ReferenceBase.createFromXsd(new XsdEnumeration(parser, convertNodeMap(node.get_Attributes())));
	}
}