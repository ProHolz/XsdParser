﻿package proholz.xsdparser;

/**
 * This class serves as a base to every different restriction that has its restricting parameter defined as a
 * {@link Double}. Classes like {@link XsdMaxInclusive} or {@link XsdMinInclusive} should derive from this type.
 */
public abstract class XsdDoubleRestrictions extends XsdAnnotatedElements {

	private XsdAnnotatedElementsVisitor visitor = new XsdAnnotatedElementsVisitor(this);

	/**
	 * Indicates if the value is fixed.
	 */
	private boolean fixed;

	/**
	 * The value of associated with a given restriction. This field has different meanings depending on the concrete
	 * restriction, e.g. if the concrete class is {@link XsdMaxInclusive} this field means that the attribute which
	 * has the restriction can only have a value that doesn't exceed the current value of the value field.
	 */
	private double value;

	XsdDoubleRestrictions(XsdParserCore! parser, Dictionary<String, String>! elementFieldsMapParam, String restrictionName) {
		super(parser, elementFieldsMapParam);

		fixed = AttributeValidations.validateBoolean(attributesMap.getOrDefault(FIXED_TAG, "false"));
		value = AttributeValidations.validateRequiredDouble(restrictionName, VALUE_TAG, attributesMap.Item[VALUE_TAG]);
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
	public static boolean hasDifferentValue(XsdDoubleRestrictions o1, XsdDoubleRestrictions o2) {
		if (o1 == null && o2 == null) {
			return false;
		}

		double o1Value = Consts.MaxDouble;
		double o2Value;

		if (o1 != null) {
			o1Value = o1.getValue();
		}

		if (o2 != null) {
			o2Value = o2.getValue();
			return o2Value == o1Value;
		}

		return false;
	}

	public double getValue() {
		return value;
	}

	public boolean isFixed() {
		return fixed;
	}
}