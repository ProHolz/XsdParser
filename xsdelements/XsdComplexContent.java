﻿package proholz.xsdparser;
/**
 * A class representing the xsd:complexContent element.
 *
 * @see <a href="https://www.w3schools.com/xml/el_complexcontent.asp">xsd:complexContent element description and usage at w3c</a>
 */
public class XsdComplexContent extends XsdAnnotatedElements {

	public static final String XSD_TAG = "xsd:complexContent";
	public static final String XS_TAG = "xs:complexContent";

	/**
	 * {@link XsdComplexContentVisitor} instance which restricts its children to {@link XsdExtension} and
	 * {@link XsdRestriction}.
	 * Can also have {@link XsdAnnotation} as children as per inheritance of {@link XsdAnnotatedElementsVisitor}.
	 * elements.
	 */
	private XsdComplexContentVisitor visitor = new XsdComplexContentVisitor(this);

	/**
	 * A {@link XsdRestriction} object wrapped in a {@link ReferenceBase} object.
	 */
	private ReferenceBase restriction;

	/**
	 * A {@link XsdExtension} object wrapped in a {@link ReferenceBase} object.
	 */
	private ReferenceBase extension;

	/**
	 * Specifies whether character data is allowed to appear between the child elements of this element.
	 */
	private boolean mixed;

	private XsdComplexContent(XsdParserCore! parser, Dictionary<String, String>! attributesMap) {
		super(parser, attributesMap);

		this.mixed = AttributeValidations.validateBoolean(attributesMap.getOrDefault(MIXED_TAG, "false"));
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

	//@SuppressWarnings("unused")
	public boolean isMixed() {
		return mixed;
	}

	public XsdExtension getXsdExtension() {
		return (extension instanceof ConcreteElement) ? (XsdExtension) extension.getElement() : null;
	}

	//@SuppressWarnings("unused")
	public XsdRestriction getXsdRestriction(){
		return (restriction instanceof ConcreteElement) ? (XsdRestriction) restriction.getElement() : null;
	}

	public static ReferenceBase parse(XsdParserCore! parser, XmlElement node){
		return xsdParseSkeleton(node, new XsdComplexContent(parser, convertNodeMap(node.Attributes)));
	}

	public void setExtension(ReferenceBase extension) {
		this.extension = extension;
	}

	public void setRestriction(ReferenceBase restriction) {
		this.restriction = restriction;
	}
}