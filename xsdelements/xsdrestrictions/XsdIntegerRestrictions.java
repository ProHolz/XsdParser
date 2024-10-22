﻿package proholz.xsdparser;

/**
 * This class serves as a base to every different restriction that has its restricting parameter defined as an {@link Integer}.
 * Classes like {@link XsdMaxLength} or {@link XsdLength} should extend this class.
 * i.e. xsd:maxLength or xsd:length.
 */
public class XsdIntegerRestrictions extends XsdAnnotatedElements {

	private XsdAnnotatedElementsVisitor visitor = new XsdAnnotatedElementsVisitor(this);

	/**
	 * Indicates if the value is fixed.
	 */
	private boolean fixed;

	/**
	 * The value of associated with a given restriction. This field has different meanings depending on the concrete
	 * restriction, e.g. if the concrete class is {@link XsdLength} this field means that the attribute which
	 * has the restriction can only have the length specified in this field..
	 */
	protected int value;

	XsdIntegerRestrictions(XsdParserCore! parser, Dictionary<String, String>! elementFieldsMapParam) {
		super(parser, elementFieldsMapParam);

		fixed = AttributeValidations.validateBoolean(attributesMap.getOrDefault(FIXED_TAG, "false"));
	}

	@Override
	public XsdAbstractElementVisitor getVisitor() {
		return visitor;
	}

	/**
	 * Compares two different objects of this type.
	 * @param o1 The first object.
	 * @param o2 The object to compare.
	 * @return True if the value of both classes is different, False if the value is equal.
	 */
	public static boolean hasDifferentValue(XsdIntegerRestrictions o1, XsdIntegerRestrictions o2) {
		if (o1 == null && o2 == null) {
			return false;
		}

		int o1Value = Consts.MaxInt32;
		int o2Value;

		if (o1 != null) {
			o1Value = o1.getValue();
		}

		if (o2 != null) {
			o2Value = o2.getValue();
			return o2Value == o1Value;
		}

		return false;
	}

	public int getValue() {
		return value;
	}

	public boolean isFixed() {
		return fixed;
	}
}