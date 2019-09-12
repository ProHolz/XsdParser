package proholz.xsdparser;

/**
 * A class representing the xsd:union element.
 *
 * @see <a href="https://www.w3schools.com/xml/el_union.asp">xsd:union description and usage at w3c</a>
 */
public class XsdUnion extends XsdAnnotatedElements {

	public static final String XSD_TAG = "xsd:union";
	public static final String XS_TAG = "xs:union";

	/**
	 * {@link XsdUnionVisitor} instance which restricts its children to {@link XsdSimpleType} instances.
	 */
	private XsdUnionVisitor visitor = new XsdUnionVisitor(this);

	/**
	 * A List of {@link XsdSimpleType} instances that represent the {@link XsdUnion}.
	 */
	private List<XsdSimpleType> simpleTypeList = new List<XsdSimpleType>();

	/**
	 * Specifies a list of built-in data types or {@link XsdSimpleType} instance names defined in a XsdSchema.
	 */
	private String memberTypes;

	private XsdUnion(XsdParserCore! parser, Dictionary<String, String>! attributesMap) {
		super(parser, attributesMap);

		this.memberTypes = attributesMap.getOrDefault(MEMBER_TYPES_TAG, memberTypes);
	}

	@Override
	public XsdAbstractElementVisitor getVisitor() {
		return visitor;
	}

	@Override
	public void accept(XsdAbstractElementVisitor visitorParam) {
		super.accept(visitorParam);
		visitorParam.visit(this);
	}

	public List<XsdSimpleType> getUnionElements(){
		return simpleTypeList;
	}

	//@SuppressWarnings("unused")
	public List<String> getMemberTypesList() {
		//return Arrays.asList(memberTypes.Split(" "));
		return memberTypes.Split(" ").ToList();
	}

	public static ReferenceBase parse(XsdParserCore! parser, XmlElement node){
		return xsdParseSkeleton(node, new XsdUnion(parser, convertNodeMap(node.Attributes)));
	}

	public void add(XsdSimpleType simpleType) {
		simpleTypeList.Add(simpleType);
	}
}