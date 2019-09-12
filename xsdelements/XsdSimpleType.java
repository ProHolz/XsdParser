package proholz.xsdparser;

/**
 * A class representing the xsd:simpleType element.
 *
 * @see <a href="https://www.w3schools.com/xml/el_simpleType.asp">xsd:simpleType description and usage at W3C</a>
 */
public class XsdSimpleType extends XsdNamedElements {

	public static final String XSD_TAG = "xsd:simpleType";
	public static final String XS_TAG = "xs:simpleType";

	/**
	 * {@link XsdSimpleTypeVisitor} instance which restricts its children to {@link XsdList}, {@link XsdUnion} or
	 * {@link XsdRestriction} instances. Can also have {@link XsdAnnotation} as children as per inheritance of
	 * {@link XsdAnnotatedElementsVisitor}.
	 */
	private XsdSimpleTypeVisitor visitor = new XsdSimpleTypeVisitor(this);

	/**
	 * A {@link XsdRestriction} instance that is present in the {@link XsdSimpleType} instance.
	 */
	private XsdRestriction restriction;

	/**
	 * A {@link XsdUnion} instance that is present in the {@link XsdSimpleType} instance.
	 */
	private XsdUnion union;

	/**
	 * A {@link XsdList} instance that is present in the {@link XsdSimpleType} instance.
	 */
	private XsdList list;

	/**
	 * Prevents other elements to derive depending on its value.
	 */
	private SimpleTypeFinalEnum finalObj;

	private XsdSimpleType(XsdParserCore! parser, Dictionary<String, String>! attributesMap) {
		super(parser, attributesMap);

		String finalDefault = AttributeValidations.getFinalDefaultValue(parent);

		this.finalObj = AttributeValidations.belongsToEnum(SimpleTypeFinalEnum.all, attributesMap.getOrDefault(FINAL_TAG, finalDefault));
	}

	private XsdSimpleType(XsdAbstractElement parent, XsdParserCore parser, Dictionary<String, String>! elementFieldsMapParam) {
		this(parser, elementFieldsMapParam);
		setParent(parent);
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
	 * Asserts that the current object has the required name attribute when not being a direct child of the XsdSchema element.
	 * Throws an exception if the required attribute is not present.
	 */
	private void rule2() {
		if (!(parent instanceof XsdSchema) && name != null){
			throw new ParsingException(XSD_TAG + " element: The " + NAME_TAG + " should only be used when the parent of the " + XSD_TAG + " is the " + XsdSchema.XSD_TAG + " element." );
		}
	}

	/**
	 * Asserts if the current has no value for its name attribute while being a direct child of the top level XsdSchema element,
	 * which is required. Throws an exception if no name is present.
	 */
	private void rule3() {
		if (parent instanceof XsdSchema && name == null){
			throw new ParsingException(XSD_TAG + " element: The " + NAME_TAG + " should is required the parent of the " + XSD_TAG + " is the " + XsdSchema.XSD_TAG + " element." );
		}
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

		XsdSimpleType copy = new XsdSimpleType(this.parent, this.parser, placeHolderAttributes);

		copy.union = this.union;
		copy.list = this.list;
		copy.restriction = this.restriction;

		return copy;
	}

	public static ReferenceBase parse(XsdParserCore! parser, XmlElement node){
		return xsdParseSkeleton(node, new XsdSimpleType(parser, convertNodeMap(node.Attributes)));
	}

	public XsdRestriction getRestriction() {
		return restriction;
	}

	public XsdUnion getUnion() {
		return union;
	}

	public XsdList getList() {
		if (this.list == null && this.union != null){
			XsdSimpleType simpleType = union.getUnionElements().Where(xsdSimpleType -> xsdSimpleType.list != null).FirstOrDefault();

			if (simpleType != null){
				return simpleType.list;
			}
		}

		return this.list;
	}

	/**
	 * This method obtains all the restrictions for the current {@link XsdSimpleType} element. It also joins multiple
	 * restrictions with the same base attribute in the same {@link XsdRestriction} object, if a overlap doesn't occur.
	 * In case of restriction overlap an exception is thrown because the information on the xsd file is contradictory.
	 * @return A list of restrictions.
	 */
	public List<XsdRestriction> getAllRestrictions() {
		Dictionary<String, XsdRestriction> restrictions = new Dictionary<String, XsdRestriction>();
		Dictionary<String, String> xsdBuiltinTypes = XsdParserCore.getXsdTypesToCodeGen();

		if (restriction != null){
			restrictions.Add(xsdBuiltinTypes.Item[restriction.getBase()], restriction);
		}

		if (union != null){
			union.getUnionElements().ForEach(unionMember -> {
				XsdRestriction unionMemberRestriction = unionMember.getRestriction();

				if (unionMemberRestriction != null){
					XsdRestriction existingRestriction = restrictions.getOrDefault(xsdBuiltinTypes.Item[unionMemberRestriction.getBase()], null);

					if (existingRestriction != null){
						if (existsRestrictionOverlap(existingRestriction, unionMemberRestriction)){
							throw new InvalidParameterException("The xsd file is invalid because has contradictory restrictions.");
						}

						updateExistingRestriction(existingRestriction, unionMemberRestriction);
					} else {
						restrictions.Add(xsdBuiltinTypes.Item[unionMemberRestriction.getBase()], unionMemberRestriction);
					}
				}
			});
		}

		return new List<XsdRestriction>(restrictions.Values);
	}

	/**
	 * Joins two distinct {@link XsdRestriction} instances. This method assumes that the information of both
	 * {@link XsdRestriction} objects don't have overlapping or contradictory information.
	 * @param existing The existing restriction.
	 * @param newRestriction The new restriction.
	 */
	private void updateExistingRestriction(XsdRestriction existing, XsdRestriction newRestriction) {
		XsdPattern pattern = newRestriction.getPattern();
		XsdMaxExclusive maxExclusive = newRestriction.getMaxExclusive();
		XsdMaxInclusive maxInclusive = newRestriction.getMaxInclusive();
		XsdMaxLength maxLength = newRestriction.getMaxLength();
		XsdMinExclusive minExclusive = newRestriction.getMinExclusive();
		XsdMinInclusive minInclusive = newRestriction.getMinInclusive();
		XsdMinLength minLength = newRestriction.getMinLength();
		XsdLength length = newRestriction.getLength();
		XsdFractionDigits fractionDigits = newRestriction.getFractionDigits();
		XsdTotalDigits totalDigits = newRestriction.getTotalDigits();
		XsdWhiteSpace whiteSpace = newRestriction.getWhiteSpace();

		if (pattern != null){
			existing.setPattern(pattern);
		}

		if (maxExclusive != null){
			existing.setMaxExclusive(maxExclusive);
		}

		if (maxInclusive != null){
			existing.setMaxInclusive(maxInclusive);
		}

		if (maxLength != null){
			existing.setMaxLength(maxLength);
		}

		if (minExclusive != null){
			existing.setMinExclusive(minExclusive);
		}

		if (minInclusive != null){
			existing.setMinInclusive(minInclusive);
		}

		if (minLength != null){
			existing.setMinLength(minLength);
		}

		if (length != null){
			existing.setLength(length);
		}

		if (fractionDigits != null){
			existing.setFractionDigits(fractionDigits);
		}

		if (totalDigits != null){
			existing.setTotalDigits(totalDigits);
		}

		if (whiteSpace != null){
			existing.setWhiteSpace(whiteSpace);
		}

		updateExistingRestrictionEnumerations(existing, newRestriction);
	}

	/**
	 * Updates the existing {@link XsdRestriction} with the restrictions of the new {@link XsdRestriction} instance.
	 * @param existing The existing {@link XsdRestriction} instance.
	 * @param newRestriction The new {@link XsdRestriction} instance.
	 */
	private void updateExistingRestrictionEnumerations(XsdRestriction existing, XsdRestriction newRestriction) {
		List<XsdEnumeration> existingEnumeration = existing.getEnumeration();
		List<XsdEnumeration> newRestrictionEnumeration = newRestriction.getEnumeration();

		if (existingEnumeration == null){
			existing.setEnumeration(newRestrictionEnumeration);
		} else {
			if (newRestrictionEnumeration != null){
				for (XsdEnumeration enumerationElem : newRestrictionEnumeration){
					if (existingEnumeration.Any( // noneMatch
						existingEnumerationElem -> !existingEnumerationElem.getValue().Equals(enumerationElem.getValue())
						)
						){
						existingEnumeration.Add(enumerationElem);
					}
				}
			}
		}
	}

	/**
	 * Checks for any restriction overlap between two different {@link XsdRestriction} instances.
	 * @param existing The existing restriction.
	 * @param newRestriction The second restriction found.
	 * @return True if an overlap between the restrictions occur, false if it doesn't occur.
	 */
	private boolean existsRestrictionOverlap(XsdRestriction existing, XsdRestriction newRestriction) {
		return

			hasDifferentValue(existing.getPattern(), newRestriction.getPattern()) ||
			   hasDifferentValue(existing.getWhiteSpace(), newRestriction.getWhiteSpace()) ||
			   hasDifferentValue(existing.getTotalDigits(), newRestriction.getTotalDigits()) ||
			   hasDifferentValue(existing.getFractionDigits(), newRestriction.getFractionDigits()) ||
			   hasDifferentValue(existing.getMaxExclusive(), newRestriction.getMaxExclusive()) ||
			   hasDifferentValue(existing.getMaxInclusive(), newRestriction.getMaxInclusive()) ||
			   hasDifferentValue(existing.getMaxLength(), newRestriction.getMaxLength()) ||
			   hasDifferentValue(existing.getMinExclusive(), newRestriction.getMinExclusive()) ||
			   hasDifferentValue(existing.getMinInclusive(), newRestriction.getMinInclusive()) ||
			   hasDifferentValue(existing.getMinLength(), newRestriction.getMinLength()) ||
			   hasDifferentValue(existing.getLength(), newRestriction.getLength());
	}

	public void setList(XsdList list) {
		this.list = list;
	}

	public void setUnion(XsdUnion union) {
		this.union = union;
	}

	public void setRestriction(XsdRestriction restriction) {
		this.restriction = restriction;
	}

	//@SuppressWarnings("unused")
	public String getFinalObj() {
		return finalObj.ToString();
	}
}