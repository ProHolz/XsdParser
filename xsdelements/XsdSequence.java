package proholz.xsdparser;

/**
 * A class representing the xsd:sequence element. Since it shares the same attributes as {@link XsdAll} or
 * {@link XsdChoice} it extends {@link XsdMultipleElements}. For more information check {@link XsdMultipleElements}.
 *
 * @see <a href="https://www.w3schools.com/xml/el_sequence.asp">xsd:sequence description and usage at w3c</a>
 */
public class XsdSequence extends XsdMultipleElements{

	public static final String XSD_TAG = "xsd:sequence";
	public static final String XS_TAG = "xs:sequence";

	/**
	 * {@link XsdSequenceVisitor} instance which restricts the children type to {@link XsdElement}, {@link XsdGroup},
	 * {@link XsdSequence} or {@link XsdChoice}.
	 * Can also have {@link XsdAnnotation} as children as per inheritance of {@link XsdAnnotatedElementsVisitor}.
	 */
	private XsdSequenceVisitor visitor = new XsdSequenceVisitor(this);

	/**
	 * Specifies the minimum number of times this element can occur in the parent element. The value can be any
	 * number bigger or equal to 0. Default value is 1. This attribute cannot be used if the parent element is the
	 * XsdSchema element.
	 */
	private Integer minOccurs;

	/**
	 * Specifies the maximum number of times this element can occur in the parent element. The value can be any
	 * number bigger or equal to 0, or if you want to set no limit on the maximum number, use the value "unbounded".
	 * Default value is 1. This attribute cannot be used if the parent element is the XsdSchema element.
	 */
	private String maxOccurs;

	private XsdSequence(XsdParserCore! parser, Dictionary<String, String>! attributesMap) {
		super(parser, attributesMap);

		this.minOccurs = AttributeValidations.validateNonNegativeInteger(XSD_TAG, MIN_OCCURS_TAG, attributesMap.getOrDefault(MIN_OCCURS_TAG, "1"));
		this.maxOccurs = AttributeValidations.maxOccursValidation(XSD_TAG, attributesMap.getOrDefault(MAX_OCCURS_TAG, "1"));
	}

	@Override
	public void accept(XsdAbstractElementVisitor visitorParam) {
		super.accept(visitorParam);
		visitorParam.visit(this);
	}

	@Override
	public XsdAbstractElementVisitor getVisitor() {
		return visitor;
	}

	public static ReferenceBase parse(XsdParserCore! parser, XmlElement node){
		return xsdParseSkeleton(node, new XsdSequence(parser, convertNodeMap(node.Attributes)));
	}

	//@SuppressWarnings("unused")
	public Integer getMinOccurs() {
		return minOccurs;
	}

	//@SuppressWarnings("unused")
	public String getMaxOccurs() {
		return maxOccurs;
	}

	/**
	 * @return The children elements that are of the type {@link XsdChoice}.
	 */
	//@SuppressWarnings("unused")
	public ISequence<XsdChoice> getChildrenChoices(){
		return getElements()

				.Where(element -> element.getElement() instanceof XsdChoice)
				.Select(element -> (XsdChoice) element.getElement());
	}

	/**
	 * @return The children elements that are of the type {@link XsdElement}.
	 */
	//@SuppressWarnings("unused")
	public ISequence<XsdElement> getChildrenElements(){
		return getElements()

				.Where(element -> element.getElement() instanceof XsdElement)
				.Select(element -> (XsdElement) element.getElement());
	}

	/**
	 * @return The children elements that are of the type {@link XsdSequence}.
	 */
	//@SuppressWarnings("unused")
	public ISequence<XsdSequence> getChildrenSequences(){
		return getElements()

				.Where(element -> element.getElement() instanceof XsdSequence)
				.Select(element -> (XsdSequence) element.getElement());
	}

	/**
	 * @return The children elements that are of the type {@link XsdGroup}.
	 */
	//@SuppressWarnings("unused")
	public ISequence<XsdGroup> getChildrenGroups(){
		return getElements()

				.Where(element -> element.getElement() instanceof XsdGroup)
				.Select(element -> (XsdGroup) element.getElement());
	}
}