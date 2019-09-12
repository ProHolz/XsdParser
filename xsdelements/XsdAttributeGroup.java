package proholz.xsdparser;

/**
 * A class is representing xsd:attributeGroup elements. It can have a ref attribute and therefore extends from
 * {@link XsdNamedElements}, which serves as a base to every element type that can have a ref attribute. For more
 * information check {@link XsdNamedElements}.
 *
 * @see <a href="https://www.w3schools.com/xml/el_attributegroup.asp">xsd:attributeGroup element description and usage at w3c</a>
 */
public class XsdAttributeGroup extends XsdNamedElements {

	public static final String XSD_TAG = "xsd:attributeGroup";
	public static final String XS_TAG = "xs:attributeGroup";

	/**
	 * {@link XsdAttributeGroupVisitor} instance which limits its children to {@link XsdAttribute} instances.
	 * Can also have {@link XsdAnnotation} as children as per inheritance of {@link XsdAnnotatedElementsVisitor}.
	 */
	private final XsdAttributeGroupVisitor visitor = new XsdAttributeGroupVisitor(this);

	/**
	 * A list of {@link XsdAttributeGroup} children instances.
	 */
	//This list is populated by the replaceUnsolvedElements and never directly (such as a Visitor method like all else).
	//The UnsolvedReference is placed in the XsdParser queue by the default implementation of the Visitor#visit(XsdAttributeGroup element)
	//The reference solving process then sends the XsdAttributeGroup to this class.
	private List<XsdAttributeGroup> attributeGroups = new List<XsdAttributeGroup>();

	/**
	 * A list of {@link XsdAttribute} children instances.
	 */
	private List<ReferenceBase> attributes = new List<ReferenceBase>();

	private XsdAttributeGroup(XsdParserCore! parser, Dictionary<String, String>!! attributesMap) {
		super(parser, attributesMap);
	}

	private XsdAttributeGroup(XsdAbstractElement parent, XsdParserCore! parser, Dictionary<String, String>! attributesMap) {
		super(parser, attributesMap);
		setParent(parent);
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

	/**
	 * @return A list of all {@link XsdAttribute} objects contained in the current {@link XsdAttributeGroup} instance,
	 * either directly or present in its children {@link XsdAttributeGroup} in the
	 * {@link XsdAttributeGroup#attributeGroups} field.
	 */
	@Override
	public List<ReferenceBase> getElements() {
		List<ReferenceBase> allAttributes = new List<ReferenceBase>();

		attributeGroups.ForEach(attributeGroup -> allAttributes.Add(attributeGroup.getElements()));

		allAttributes.Add(attributes);

		return allAttributes;
	}

	/**
	 * Performs a copy of the current object for replacing purposes. The cloned objects are used to replace
	 * {@link UnsolvedReference} objects in the reference solving process.
	 * @param placeHolderAttributes The additional attributes to add to the clone.
	 * @return A copy of the object from which is called upon.
	 */
	@Override
	public XsdNamedElements clone(Dictionary<String, String>! placeHolderAttributes) {
		placeHolderAttributes.Add(attributesMap);
		placeHolderAttributes.Remove(REF_TAG);

		XsdAttributeGroup elementCopy = new XsdAttributeGroup(this.parent, this.parser, placeHolderAttributes);

		elementCopy.attributes.Add(this.attributes);
		elementCopy.attributeGroups.Add(this.attributeGroups);

		return elementCopy;
	}

	@Override
	public void replaceUnsolvedElements(NamedConcreteElement element) {
		if (element.getElement() instanceof  XsdAttributeGroup){
			XsdAttributeGroup attributeGroup = (XsdAttributeGroup) element.getElement();

			attributeGroup.attributes.ForEach(attribute -> attribute.getElement().setParent(attributeGroup));

			this.attributeGroups.Add(attributeGroup);
		}
	}

	//@SuppressWarnings("unused")
	public List<XsdAttributeGroup> getAttributeGroups() {
		return attributeGroups;
	}

	/**
	 * @return All the attributes of this attributeGroup and other attributeGroups contained within.
	 */
	//@SuppressWarnings("unused")
	public ISequence<XsdAttribute> getAllAttributes(){
		return getElements()

				.Where(element -> element.getElement() instanceof XsdAttribute)
				.Select(element -> (XsdAttribute) element.getElement());
	}

	/**
	 * @return The attributes directly defined in this attributeGroup.
	 */
	//@SuppressWarnings("unused")
	public ISequence<XsdAttribute> getDirectAttributes(){
		return attributes

					.Where(element -> element.getElement() instanceof XsdAttribute)
					.Select(element -> (XsdAttribute) element.getElement());
	}

	public static ReferenceBase parse(XsdParserCore! parser, XmlElement node) {
		return xsdParseSkeleton(node, new XsdAttributeGroup(parser, convertNodeMap(node.Attributes)));
	}

	public void addAttribute(ReferenceBase attribute) {
		attributes.Add(attribute);
	}
}