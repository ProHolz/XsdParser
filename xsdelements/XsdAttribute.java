package proholz.xsdparser;

/**
 * A class representing the xsd:attribute element. It can have a ref attribute and therefore extends from
 * {@link XsdNamedElements}, which serves as a base to every element type that can have a ref attribute.
 * For more information check {@link XsdNamedElements}.
 *
 * @see <a href="https://www.w3schools.com/xml/el_attribute.asp">xsd:attribute element description and usage at w3c</a>
 */
public class XsdAttribute extends XsdNamedElements {

	public static final String XSD_TAG = "xsd:attribute";
	public static final String XS_TAG = "xs:attribute";

	/**
	 * {@link XsdAttributeVisitor} instance which restricts the its children to {@link XsdSimpleType}.
	 * Can also have xsd:annotation as children as per inheritance of {@link XsdAnnotatedElementsVisitor}.
	 */
	private XsdAttributeVisitor visitor = new XsdAttributeVisitor(this);

	/**
	 * A {@link XsdSimpleType} instance wrapped in a {@link ReferenceBase} object which indicate any restrictions
	 * that may be present in the current {@link XsdAttribute} instance.
	 */
	private ReferenceBase simpleType;

	/**
	 * A default value for the current {@link XsdAttribute} instance. This value and {@link XsdAttribute#fixed}
	 * shouldn't be present at the same time.
	 */
	private String defaultElement;

	/**
	 * Specifies a fixed value for the current {@link XsdAttribute} instance. This value and
	 * {@link XsdAttribute#defaultElement} shouldn't be present at the same time.
	 */
	private String fixed;

	/**
	 * Specifies either a built-in data type for the current {@link XsdAttribute} instance or serves as a reference to a
	 * {@link XsdSimpleType} instance. In the case of being used as a reference to a {@link XsdSimpleType} instance
	 * its value is used to create an {@link UnsolvedReference} using its value as ref to be resolved later in the
	 * parsing process.
	 */
	private String type;

	/**
	 * Specifies if the current {@link XsdAttribute} attribute is "qualified" or "unqualified".
	 */
	private FormEnum form;

	/**
	 * Specifies how this {@link XsdAttribute} should be used. The possible values are: required, prohibited, optional.
	 */
	private UsageEnum use;

	private XsdAttribute( XsdParserCore! parser,  Dictionary<String, String>! attributesMap) {
		super(parser, attributesMap);

		String formDefaultValue = getFormDefaultValue(parent);

		this.defaultElement = attributesMap.getOrDefault(DEFAULT_ELEMENT_TAG, defaultElement);
		this.fixed = attributesMap.getOrDefault(FIXED_TAG, fixed);
		this.type = attributesMap.getOrDefault(TYPE_TAG, type);
		this.form = AttributeValidations.belongsToEnum(FormEnum.qualified, attributesMap.getOrDefault(FORM_TAG, formDefaultValue));
		this.use = AttributeValidations.belongsToEnum(UsageEnum.optional, attributesMap.getOrDefault(USE_TAG, UsageEnum.optional.ToString()));

		if (type != null && !XsdParser.getXsdTypesToJava().ContainsKey(type)){
			this.simpleType = new UnsolvedReference(type, new XsdAttribute(this, parser, new Dictionary<String, String>()));
			parser.addUnsolvedReference((UnsolvedReference) this.simpleType);
		}
	}

	private XsdAttribute(XsdAbstractElement parent,  XsdParserCore! parser,  Dictionary<String, String>! attributesMap) {
		this(parser, attributesMap);
		setParent(parent);
	}

	private static String getFormDefaultValue(XsdAbstractElement parent) {
		if (parent == null) return null;

		if (parent instanceof XsdElement){
			return ((XsdElement) parent).getForm();
		}

		if (parent instanceof XsdSchema){
			return ((XsdSchema) parent).getAttributeFormDefault();
		}

		return getFormDefaultValue(parent.getParent());
	}

	/**
	 * Runs verifications on each concrete element to ensure that the XSD schema rules are verified.
	 */
	@Override
	public void validateSchemaRules() {
		super.validateSchemaRules();

		rule2();
		rule3();
	}

	/**
	 * Asserts if the current object has a ref attribute at the same time as either a simpleType as children, a form attribute or a type attribute.
	 * Throws an exception in that case.
	 */
	private void rule3() {
		if (attributesMap.ContainsKey(REF_TAG) && (simpleType != null  || type != null)){
			throw new ParsingException(XSD_TAG + " element: If " + REF_TAG + " attribute is present, simpleType element, form attribute and type attribute cannot be present at the same time.");
		}
	}

	/**
	 * Asserts if the current object has the fixed and default attributes at the same time, which isn't allowed, throwing
	 * an exception in that case.
	 */
	private void rule2() {
		if (fixed != null && defaultElement != null){
			throw new ParsingException(XSD_TAG + " element: " + FIXED_TAG + " and " + DEFAULT_ELEMENT_TAG + " attributes are not allowed at the same time.");
		}
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
	 * Performs a copy of the current object for replacing purposes. The cloned objects are used to replace
	 * {@link UnsolvedReference} objects in the reference solving process.
	 * @param placeHolderAttributes The additional attributes to add to the clone.
	 * @return A copy of the object from which is called upon.
	 */
	@Override
	public XsdNamedElements clone( Dictionary<String, String>! placeHolderAttributes) {
		placeHolderAttributes.Add(attributesMap);
		placeHolderAttributes.Remove(TYPE_TAG);
		placeHolderAttributes.Remove(REF_TAG);

		XsdAttribute copy = new XsdAttribute(this.parent, this.parser, placeHolderAttributes);

		copy.type = this.type;
		copy.simpleType = this.simpleType;

		return copy;
	}

	/**
	 * Receives a {@link NamedConcreteElement} that should be the one requested earlier.
	 *  * In the {@link XsdAttribute} constructor:
	 *      this.simpleType = new UnsolvedReference(type, placeHolder);
	 *      XsdParser.getInstance().addUnsolvedReference((UnsolvedReference) this.simpleType);
	 * This implies that the object being received is the object that is being referred with the {@link XsdAttribute#type}
	 * String.
	 * @param elementWrapper The object that should be wrapping the requested {@link XsdSimpleType} object.
	 */
	@Override
	public void replaceUnsolvedElements(NamedConcreteElement elementWrapper) {
		super.replaceUnsolvedElements(elementWrapper);

		XsdAbstractElement element = elementWrapper.getElement();

		if (element instanceof XsdSimpleType && simpleType != null && compareReference(elementWrapper, type)){
			this.simpleType = elementWrapper;
		}
	}

	public void setSimpleType(ReferenceBase simpleType) {
		this.simpleType = simpleType;
	}

	public XsdSimpleType getXsdSimpleType(){
		return (simpleType instanceof ConcreteElement) ? (XsdSimpleType) simpleType.getElement() : null;
	}

	public String getType() {
		return type;
	}

	public String getUse() {
		return use.ToString();
	}

	public String getForm() {
		return form.ToString();
	}

	public String getFixed() {
		return fixed;
	}

	//@SuppressWarnings("unused")
	public List<XsdRestriction> getAllRestrictions(){
		XsdSimpleType simpleTypeObj = getXsdSimpleType();

		if (simpleTypeObj != null){
			return simpleTypeObj.getAllRestrictions();
		}

		return new List<XsdRestriction>();//Collections.emptyList();
	}

	public static ReferenceBase parse( XsdParserCore! parser, XmlElement node) {
		return xsdParseSkeleton(node, new XsdAttribute(parser, convertNodeMap(node.get_Attributes())));
	}

}