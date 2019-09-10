package proholz.xsdparser;

public __partial class AttributeValidations {

	private AttributeValidations(){ }

	/**
	 * Checks if the maxOccurs attribute is unbounded or an {@link Integer} value.
	 * @param value The possible maxOccurs value.
	 * @param elementName The name of the element containing the maxOccurs attribute.
	 * @return The validated maxOccurs value.
	 */
	static String maxOccursValidation(String elementName, String value){
		if (value.Equals("unbounded")){
			return value;
		}

		validateNonNegativeInteger(elementName, XsdAbstractElement.MAX_OCCURS_TAG, value);

		return value;
	}

	/**
	 * Validates if a given String is a non negative {@link Integer}. Throws an exception if the {@link String} isn't a non
	 * negative {@link Integer}.
	 * @param elementName The element name containing the field with the {@link Integer} value.
	 * @param attributeName The name of the attribute with the {@link Integer}.
	 * @param value The value to be parsed to a {@link Integer} object.
	 * @return The parsed {@link Integer} value.
	 */
	static Integer validateNonNegativeInteger(String elementName, String attributeName, String value){
		try {
			int intValue = Integer.Parse(value);

			if (intValue < 0){
				throw new ParsingException("The " + elementName + " " + attributeName + " attribute should be a non negative integer. (greater or equal than 0)");
			}

			return intValue;
		} catch (Exception e){
			throw new ParsingException("The " + elementName + " " + attributeName + "  attribute should be a non negative integer.");
		}
	}

	/**
	 * Validates if a given String is a non negative {@link Integer}. Throws an exception if the {@link String} isn't a
	 * non negative {@link Integer}.
	 * @param elementName The element name containing the field with the {@link Integer} value.
	 * @param attributeName The name of the attribute with the {@link Integer}.
	 * @param value The value to be parsed to a {@link Integer} object.
	 * @return The parsed {@link Integer} value.
	 */
	public static Integer validateRequiredNonNegativeInteger(String elementName, String attributeName, String value){
		if (value == null) throw new ParsingException(attributeMissingMessage(elementName, attributeName));

		return validateNonNegativeInteger(elementName, attributeName, value);
	}

	/**
	 * Validates if a given String is a positive {@link Integer}. Throws an exception if the {@link String} isn't a
	 * positive {@link Integer}.
	 * @param elementName The element name containing the field with the {@link Integer} value.
	 * @param attributeName The name of the attribute with the {@link Integer} type.
	 * @param value The value to be parsed to a {@link Integer} object.
	 * @return The parsed {@link Integer} value.
	 */
	private static Integer validatePositiveInteger(String elementName, String attributeName, String value){
		try {
			int intValue = Integer.Parse(value);

			if (intValue <= 0){
				throw new ParsingException("The " + elementName + " " + attributeName + "  attribute should be a positive integer. (greater than 0)");
			}

			return intValue;
		} catch (Exception e){
			throw new ParsingException("The " + elementName + " " + attributeName + "  attribute should be a positive integer.");
		}
	}

	/**
	 * Validates if a given String is a positive {@link Integer}. Throws an exception if the {@link String} isn't a
	 * positive {@link Integer}.
	 * @param elementName The element name containing the field with the {@link Integer} value.
	 * @param attributeName The name of the attribute with the {@link Integer} type.
	 * @param value The value to be parsed to a {@link Integer} object.
	 * @return The parsed {@link Integer} value.
	 */
	public static Integer validateRequiredPositiveInteger(String elementName, String attributeName, String value){
		if (value == null) throw new ParsingException(attributeMissingMessage(elementName, attributeName));

		return validatePositiveInteger(elementName, attributeName, value);
	}

	public static Boolean validateBoolean(String value){
		 return Boolean.Parse(value);
	}

	/**
	 * Validates if a given {@link String} is a {@link Double}. Throws an exception if the {@link String} isn't a
	 * {@link Double}.
	 * @param elementName The element name containing the field with the {@link Double} value.
	 * @param attributeName The name of the attribute with the type {@link Double}.
	 * @param value The value to be parsed to a {@link Double} object.
	 * @return The parsed {@link Double} value.
	 */
	private static Double validateDouble(String elementName, String attributeName, String value){
		try {
			return  Convert.ToDoubleInvariant(value);
		} catch (Exception e){
			throw new ParsingException("The " + elementName + " " + attributeName + "  attribute should be a numeric value.");
		}
	}

	/**
	 * Validates if a given {@link String} is a {@link Double}. Throws an exception if the {@link String} isn't a {@link Double}.
	 * @param elementName The element name containing the field with the {@link Double} value.
	 * @param attributeName The name of the attribute with the type {@link Double}.
	 * @param value The value to be parsed to a {@link Double} object.
	 * @return The parsed {@link Double} value.
	 */
	public static Double validateRequiredDouble(String elementName, String attributeName, String value){
		if (value == null) throw new ParsingException(attributeMissingMessage(elementName, attributeName));

		return validateDouble(elementName, attributeName, value);
	}

	/**
	 * Obtains the default value of the {@link XsdSchema#attributeFormDefault} attribute by iterating in the  element tree
	 * by going from {@link XsdAbstractElement#parent} to {@link XsdAbstractElement#parent} until reaching the top level
	 * element.
	 * @param parent The parent of the element requesting the default form value.
	 * @return The default value for the form attribute.
	 */
	static String getFormDefaultValue(XsdAbstractElement parent) {
		if (parent == null) return null;

		if (parent instanceof XsdSchema){
			return ((XsdSchema)parent).getElementFormDefault();
		}

		return getFormDefaultValue(parent.getParent());
	}

	/**
	 * Obtains the default value of the {@link XsdSchema#finalDefault} attribute by iterating in the element tree by
	 * going from {@link XsdAbstractElement#parent} to {@link XsdAbstractElement#parent} until reaching the top level
	 * element.
	 * @param parent The parent of the element requesting the default final value.
	 * @return The default value for the final attribute.
	 */
	static String getFinalDefaultValue(XsdAbstractElement parent) {
		if (parent == null) return null;

		if (parent instanceof XsdSchema){
			return ((XsdSchema)parent).getFinalDefault();
		}

		return getFinalDefaultValue(parent.getParent());
	}

	/**
	 * Obtains the default value of the {@link XsdSchema#blockDefault} attribute by iterating in the element tree by
	 * going from {@link XsdAbstractElement#parent} to {@link XsdAbstractElement#parent} until reaching the top level
	 * element.
	 * @param parent The parent of the element requesting the default block value.
	 * @return The default value for the block attribute.
	 */
	static String getBlockDefaultValue(XsdAbstractElement parent) {
		if (parent == null) return null;

		if (parent instanceof XsdSchema){
			return ((XsdSchema)parent).getBlockDefault();
		}

		return getBlockDefaultValue(parent.getParent());
	}

	private static String attributeMissingMessage(String elementName, String attributeName){
		return "The " + elementName + " " + attributeName + " is required to have a value attribute.";
	}
}