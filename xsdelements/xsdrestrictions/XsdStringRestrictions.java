package proholz.xsdparser;

/**
 * This class serves as a base to every different restriction that has its restricting parameter defined as an {@link String}.
 * Classes like {@link XsdPattern} or {@link XsdEnumeration} should extend this class.
 */
public class XsdStringRestrictions extends XsdAnnotatedElements{

	private XsdAnnotatedElementsVisitor visitor = new XsdAnnotatedElementsVisitor(this);

	/**
	 * The value of associated with a given restriction. This field has different meanings depending on the concrete
	 * restriction, e.g. if the concrete class is {@link XsdEnumeration} this field means that the attribute which
	 * has the restriction can only have the value that is specified in this field.
	 */
	private String value;

	XsdStringRestrictions(XsdParserCore! parser, Dictionary<String, String>! elementFieldsMapParam) {
		super(parser, elementFieldsMapParam);

		value = attributesMap.getOrDefault(VALUE_TAG, value);
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
	public static boolean hasDifferentValue(XsdStringRestrictions o1, XsdStringRestrictions o2) {
		if (o1 == null && o2 == null) {
			return false;
		}

		String o1Value = null;
		String o2Value;

		if (o1 != null) {
			o1Value = o1.getValue();
		}

		if (o2 != null) {
			o2Value = o2.getValue();
			return o2Value.Equals(o1Value);
		}

		return false;
	}

	public String getValue() {
		return value;
	}
}