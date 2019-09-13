namespace proholz.xsdparser;

interface

type
	AttributeValidations = public partial class
	private
	constructor;
	// *
	//      * Validates if a given String is a positive {@link Integer}. Throws an exception if the {@link String} isn't a
	//      * positive {@link Integer}.
	//      * @param elementName The element name containing the field with the {@link Integer} value.
	//      * @param attributeName The name of the attribute with the {@link Integer} type.
	//      * @param value The value to be parsed to a {@link Integer} object.
	//      * @return The parsed {@link Integer} value.
	class method validatePositiveInteger(elementName: String; attributeName: String; value: String): Integer;
	// *
	//      * Validates if a given {@link String} is a {@link Double}. Throws an exception if the {@link String} isn't a
	//      * {@link Double}.
	//      * @param elementName The element name containing the field with the {@link Double} value.
	//      * @param attributeName The name of the attribute with the type {@link Double}.
	//      * @param value The value to be parsed to a {@link Double} object.
	//      * @return The parsed {@link Double} value.
	class method validateDouble(elementName: String; attributeName: String; value: String): Double;
	class method attributeMissingMessage(elementName: String; attributeName: String): String;
	assembly
	// *
	//      * Checks if the maxOccurs attribute is unbounded or an {@link Integer} value.
	//      * @param value The possible maxOccurs value.
	//      * @param elementName The name of the element containing the maxOccurs attribute.
	//      * @return The validated maxOccurs value.
	class method maxOccursValidation(elementName: String; value: String): String;
	// *
	//      * Validates if a given String is a non negative {@link Integer}. Throws an exception if the {@link String} isn't a non
	//      * negative {@link Integer}.
	//      * @param elementName The element name containing the field with the {@link Integer} value.
	//      * @param attributeName The name of the attribute with the {@link Integer}.
	//      * @param value The value to be parsed to a {@link Integer} object.
	//      * @return The parsed {@link Integer} value.
	class method validateNonNegativeInteger(elementName: String; attributeName: String; value: String): Integer;
	// *
	//      * Obtains the default value of the {@link XsdSchema#attributeFormDefault} attribute by iterating in the  element tree
	//      * by going from {@link XsdAbstractElement#parent} to {@link XsdAbstractElement#parent} until reaching the top level
	//      * element.
	//      * @param parent The parent of the element requesting the default form value.
	//      * @return The default value for the form attribute.
	class method getFormDefaultValue(parent: XsdAbstractElement): String;
	// *
	//      * Obtains the default value of the {@link XsdSchema#finalDefault} attribute by iterating in the element tree by
	//      * going from {@link XsdAbstractElement#parent} to {@link XsdAbstractElement#parent} until reaching the top level
	//      * element.
	//      * @param parent The parent of the element requesting the default final value.
	//      * @return The default value for the final attribute.
	class method getFinalDefaultValue(parent: XsdAbstractElement): String;
	// *
	//      * Obtains the default value of the {@link XsdSchema#blockDefault} attribute by iterating in the element tree by
	//      * going from {@link XsdAbstractElement#parent} to {@link XsdAbstractElement#parent} until reaching the top level
	//      * element.
	//      * @param parent The parent of the element requesting the default block value.
	//      * @return The default value for the block attribute.
	class method getBlockDefaultValue(parent: XsdAbstractElement): String;
	public
	// *
	//      * Validates if a given String is a non negative {@link Integer}. Throws an exception if the {@link String} isn't a
	//      * non negative {@link Integer}.
	//      * @param elementName The element name containing the field with the {@link Integer} value.
	//      * @param attributeName The name of the attribute with the {@link Integer}.
	//      * @param value The value to be parsed to a {@link Integer} object.
	//      * @return The parsed {@link Integer} value.
	class method validateRequiredNonNegativeInteger(elementName: String; attributeName: String; value: String): Integer;
	// *
	//      * Validates if a given String is a positive {@link Integer}. Throws an exception if the {@link String} isn't a
	//      * positive {@link Integer}.
	//      * @param elementName The element name containing the field with the {@link Integer} value.
	//      * @param attributeName The name of the attribute with the {@link Integer} type.
	//      * @param value The value to be parsed to a {@link Integer} object.
	//      * @return The parsed {@link Integer} value.
	class method validateRequiredPositiveInteger(elementName: String; attributeName: String; value: String): Integer;
	class method validateBoolean(value: String): Boolean;
	// *
	//      * Validates if a given {@link String} is a {@link Double}. Throws an exception if the {@link String} isn't a {@link Double}.
	//      * @param elementName The element name containing the field with the {@link Double} value.
	//      * @param attributeName The name of the attribute with the type {@link Double}.
	//      * @param value The value to be parsed to a {@link Double} object.
	//      * @return The parsed {@link Double} value.
	class method validateRequiredDouble(elementName: String; attributeName: String; value: String): Double;
	end;

implementation

constructor AttributeValidations;
begin
end;

class method AttributeValidations.maxOccursValidation(elementName: String; value: String): String;
begin
	if value.Equals('unbounded') then begin
	exit value;
	end;
	validateNonNegativeInteger(elementName, XsdAbstractElement.MAX_OCCURS_TAG, value);
	exit value;
end;

class method AttributeValidations.validateNonNegativeInteger(elementName: String; attributeName: String; value: String): Integer;
begin
	try
	var intValue: Integer := Convert.ToInt32(value);
	if intValue < 0 then begin
		raise new ParsingException('The ' + elementName + ' ' + attributeName + ' attribute should be a non negative integer. (greater or equal than 0)');
	end;
	exit intValue;
	except
	on e: Exception do begin
		raise new ParsingException('The ' + elementName + ' ' + attributeName + '  attribute should be a non negative integer.');
	end;
	end;
end;

class method AttributeValidations.validateRequiredNonNegativeInteger(elementName: String; attributeName: String; value: String): Integer;
begin
	if value = nil then begin
	raise new ParsingException(attributeMissingMessage(elementName, attributeName));
	end;
	exit validateNonNegativeInteger(elementName, attributeName, value);
end;

class method AttributeValidations.validatePositiveInteger(elementName: String; attributeName: String; value: String): Integer;
begin
	try
	var intValue: Integer := Convert.ToInt32(value);
	if intValue ≤ 0 then begin
		raise new ParsingException('The ' + elementName + ' ' + attributeName + '  attribute should be a positive integer. (greater than 0)');
	end;
	exit intValue;
	except
	on e: Exception do begin
		raise new ParsingException('The ' + elementName + ' ' + attributeName + '  attribute should be a positive integer.');
	end;
	end;
end;

class method AttributeValidations.validateRequiredPositiveInteger(elementName: String; attributeName: String; value: String): Integer;
begin
	if value = nil then begin
	raise new ParsingException(attributeMissingMessage(elementName, attributeName));
	end;
	exit validatePositiveInteger(elementName, attributeName, value);
end;

class method AttributeValidations.validateBoolean(value: String): Boolean;
begin
	exit Convert.ToBoolean(value);
end;

class method AttributeValidations.validateDouble(elementName: String; attributeName: String; value: String): Double;
begin
	try
	exit Convert.ToDoubleInvariant(value);
	except
	on e: Exception do begin
		raise new ParsingException('The ' + elementName + ' ' + attributeName + '  attribute should be a numeric value.');
	end;
	end;
end;

class method AttributeValidations.validateRequiredDouble(elementName: String; attributeName: String; value: String): Double;
begin
	if value = nil then begin
	raise new ParsingException(attributeMissingMessage(elementName, attributeName));
	end;
	exit validateDouble(elementName, attributeName, value);
end;

class method AttributeValidations.getFormDefaultValue(parent: XsdAbstractElement): String;
begin
	if parent = nil then begin
	exit nil;
	end;
	if parent is XsdSchema then begin
	exit XsdSchema(parent).getElementFormDefault();
	end;
	exit getFormDefaultValue(parent.getParent());
end;

class method AttributeValidations.getFinalDefaultValue(parent: XsdAbstractElement): String;
begin
	if parent = nil then begin
	exit nil;
	end;
	if parent is XsdSchema then begin
	exit XsdSchema(parent).getFinalDefault();
	end;
	exit getFinalDefaultValue(parent.getParent());
end;

class method AttributeValidations.getBlockDefaultValue(parent: XsdAbstractElement): String;
begin
	if parent = nil then begin
	exit nil;
	end;
	if parent is XsdSchema then begin
	exit XsdSchema(parent).getBlockDefault();
	end;
	exit getBlockDefaultValue(parent.getParent());
end;

class method AttributeValidations.attributeMissingMessage(elementName: String; attributeName: String): String;
begin
	exit 'The ' + elementName + ' ' + attributeName + ' is required to have a value attribute.';
end;

end.