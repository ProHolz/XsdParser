package proholz.xsdparser;



public class DefaultParserConfig implements ParserConfig {
	@Override
	public Dictionary<String, String> getXsdTypesToCodeGen() {
		Dictionary<String, String> xsdTypesToJava = new Dictionary<String, String>();

		String string = "String";
		String xmlGregorianCalendar = "XMLGregorianCalendar";
		String duration = "Duration";
		String bigInteger = "BigInteger";
		String integer = "Integer";
		String shortString = "Short";
		String qName = "QName";
		String longString = "Long";
		String byteString = "Byte";

		xsdTypesToJava.Add("xsd:anyURI", string);
		xsdTypesToJava.Add("xs:anyURI", string);
		xsdTypesToJava.Add("xsd:boolean", "Boolean");
		xsdTypesToJava.Add("xs:boolean", "Boolean");
		xsdTypesToJava.Add("xsd:date", xmlGregorianCalendar);
		xsdTypesToJava.Add("xs:date", xmlGregorianCalendar);
		xsdTypesToJava.Add("xsd:dateTime", xmlGregorianCalendar);
		xsdTypesToJava.Add("xs:dateTime", xmlGregorianCalendar);
		xsdTypesToJava.Add("xsd:time", xmlGregorianCalendar);
		xsdTypesToJava.Add("xs:time", xmlGregorianCalendar);
		xsdTypesToJava.Add("xsd:duration", duration);
		xsdTypesToJava.Add("xs:duration", duration);
		xsdTypesToJava.Add("xsd:dayTimeDuration", duration);
		xsdTypesToJava.Add("xs:dayTimeDuration", duration);
		xsdTypesToJava.Add("xsd:yearMonthDuration", duration);
		xsdTypesToJava.Add("xs:yearMonthDuration", duration);
		xsdTypesToJava.Add("xsd:gDay", xmlGregorianCalendar);
		xsdTypesToJava.Add("xs:gDay", xmlGregorianCalendar);
		xsdTypesToJava.Add("xsd:gMonth", xmlGregorianCalendar);
		xsdTypesToJava.Add("xs:gMonth", xmlGregorianCalendar);
		xsdTypesToJava.Add("xsd:gMonthDay", xmlGregorianCalendar);
		xsdTypesToJava.Add("xs:gMonthDay", xmlGregorianCalendar);
		xsdTypesToJava.Add("xsd:gYear", xmlGregorianCalendar);
		xsdTypesToJava.Add("xs:gYear", xmlGregorianCalendar);
		xsdTypesToJava.Add("xsd:gYearMonth", xmlGregorianCalendar);
		xsdTypesToJava.Add("xs:gYearMonth", xmlGregorianCalendar);
		xsdTypesToJava.Add("xsd:decimal", "BigDecimal");
		xsdTypesToJava.Add("xs:decimal", "BigDecimal");
		xsdTypesToJava.Add("xsd:integer", bigInteger);
		xsdTypesToJava.Add("xs:integer", bigInteger);
		xsdTypesToJava.Add("xsd:nonPositiveInteger", bigInteger);
		xsdTypesToJava.Add("xs:nonPositiveInteger", bigInteger);
		xsdTypesToJava.Add("xsd:negativeInteger", bigInteger);
		xsdTypesToJava.Add("xs:negativeInteger", bigInteger);
		xsdTypesToJava.Add("xsd:long", longString);
		xsdTypesToJava.Add("xs:long", longString);
		xsdTypesToJava.Add("xsd:int", integer);
		xsdTypesToJava.Add("xs:int", integer);
		xsdTypesToJava.Add("xsd:short", shortString);
		xsdTypesToJava.Add("xs:short", shortString);
		xsdTypesToJava.Add("xsd:byte", byteString);
		xsdTypesToJava.Add("xs:byte", byteString);
		xsdTypesToJava.Add("xsd:nonNegativeInteger", bigInteger);
		xsdTypesToJava.Add("xs:nonNegativeInteger", bigInteger);
		xsdTypesToJava.Add("xsd:unsignedLong", bigInteger);
		xsdTypesToJava.Add("xs:unsignedLong", bigInteger);
		xsdTypesToJava.Add("xsd:unsignedInt", longString);
		xsdTypesToJava.Add("xs:unsignedInt", longString);
		xsdTypesToJava.Add("xsd:unsignedShort", integer);
		xsdTypesToJava.Add("xs:unsignedShort", integer);
		xsdTypesToJava.Add("xsd:unsignedByte", shortString);
		xsdTypesToJava.Add("xs:unsignedByte", shortString);
		xsdTypesToJava.Add("xsd:positiveInteger", bigInteger);
		xsdTypesToJava.Add("xs:positiveInteger", bigInteger);
		xsdTypesToJava.Add("xsd:double", "Double");
		xsdTypesToJava.Add("xs:double", "Double");
		xsdTypesToJava.Add("xsd:float", "Float");
		xsdTypesToJava.Add("xs:float", "Float");
		xsdTypesToJava.Add("xsd:QName", qName);
		xsdTypesToJava.Add("xs:QName", qName);
		xsdTypesToJava.Add("xsd:NOTATION", qName);
		xsdTypesToJava.Add("xs:NOTATION", qName);
		xsdTypesToJava.Add("xsd:string", string);
		xsdTypesToJava.Add("xs:string", string);
		xsdTypesToJava.Add("xsd:normalizedString", string);
		xsdTypesToJava.Add("xs:normalizedString", string);
		xsdTypesToJava.Add("xsd:token", string);
		xsdTypesToJava.Add("xs:token", string);
		xsdTypesToJava.Add("xsd:language", string);
		xsdTypesToJava.Add("xs:language", string);
		xsdTypesToJava.Add("xsd:NMTOKEN", string);
		xsdTypesToJava.Add("xs:NMTOKEN", string);
		xsdTypesToJava.Add("xsd:Name", string);
		xsdTypesToJava.Add("xs:Name", string);
		xsdTypesToJava.Add("xsd:NCName", string);
		xsdTypesToJava.Add("xs:NCName", string);
		xsdTypesToJava.Add("xsd:ID", string);
		xsdTypesToJava.Add("xs:ID", string);
		xsdTypesToJava.Add("xsd:IDREF", string);
		xsdTypesToJava.Add("xs:IDREF", string);
		xsdTypesToJava.Add("xsd:ENTITY", string);
		xsdTypesToJava.Add("xs:ENTITY", string);
		xsdTypesToJava.Add("xsd:untypedAtomic", string);
		xsdTypesToJava.Add("xs:untypedAtomic", string);

		return xsdTypesToJava;
	}

	@Override
	public Dictionary<String, BiFunction<XsdParserCore, XmlElement, ReferenceBase>> getParseMappers() {
		Dictionary<String, BiFunction<XsdParserCore, XmlElement, ReferenceBase>> parseMappers = new Dictionary<String, BiFunction<XsdParserCore, XmlElement, ReferenceBase>>();

		parseMappers.Add(XsdAll.XSD_TAG, XsdAll::parse);
		parseMappers.Add(XsdAll.XS_TAG, XsdAll::parse);
		parseMappers.Add(XsdAttribute.XSD_TAG, XsdAttribute::parse);
		parseMappers.Add(XsdAttribute.XS_TAG, XsdAttribute::parse);
		parseMappers.Add(XsdAttributeGroup.XSD_TAG, XsdAttributeGroup::parse);
		parseMappers.Add(XsdAttributeGroup.XS_TAG, XsdAttributeGroup::parse);
		parseMappers.Add(XsdChoice.XSD_TAG, XsdChoice::parse);
		parseMappers.Add(XsdChoice.XS_TAG, XsdChoice::parse);
		parseMappers.Add(XsdComplexType.XSD_TAG, XsdComplexType::parse);
		parseMappers.Add(XsdComplexType.XS_TAG, XsdComplexType::parse);
		parseMappers.Add(XsdElement.XSD_TAG, XsdElement::parse);
		parseMappers.Add(XsdElement.XS_TAG, XsdElement::parse);
		parseMappers.Add(XsdGroup.XSD_TAG, XsdGroup::parse);
		parseMappers.Add(XsdGroup.XS_TAG, XsdGroup::parse);
		parseMappers.Add(XsdInclude.XSD_TAG, XsdInclude::parse);
		parseMappers.Add(XsdInclude.XS_TAG, XsdInclude::parse);
		parseMappers.Add(XsdImport.XSD_TAG, XsdImport::parse);
		parseMappers.Add(XsdImport.XS_TAG, XsdImport::parse);
		parseMappers.Add(XsdSequence.XSD_TAG, XsdSequence::parse);
		parseMappers.Add(XsdSequence.XS_TAG, XsdSequence::parse);
		parseMappers.Add(XsdSimpleType.XSD_TAG, XsdSimpleType::parse);
		parseMappers.Add(XsdSimpleType.XS_TAG, XsdSimpleType::parse);
		parseMappers.Add(XsdList.XSD_TAG, XsdList::parse);
		parseMappers.Add(XsdList.XS_TAG, XsdList::parse);
		parseMappers.Add(XsdRestriction.XSD_TAG, XsdRestriction::parse);
		parseMappers.Add(XsdRestriction.XS_TAG, XsdRestriction::parse);
		parseMappers.Add(XsdUnion.XSD_TAG, XsdUnion::parse);
		parseMappers.Add(XsdUnion.XS_TAG, XsdUnion::parse);

		parseMappers.Add(XsdAnnotation.XSD_TAG, XsdAnnotation::parse);
		parseMappers.Add(XsdAnnotation.XS_TAG, XsdAnnotation::parse);
		parseMappers.Add(XsdAppInfo.XSD_TAG, XsdAppInfo::parse);
		parseMappers.Add(XsdAppInfo.XS_TAG, XsdAppInfo::parse);
		parseMappers.Add(XsdComplexContent.XSD_TAG, XsdComplexContent::parse);
		parseMappers.Add(XsdComplexContent.XS_TAG, XsdComplexContent::parse);
		parseMappers.Add(XsdDocumentation.XSD_TAG, XsdDocumentation::parse);
		parseMappers.Add(XsdDocumentation.XS_TAG, XsdDocumentation::parse);
		parseMappers.Add(XsdExtension.XSD_TAG, XsdExtension::parse);
		parseMappers.Add(XsdExtension.XS_TAG, XsdExtension::parse);
		parseMappers.Add(XsdSimpleContent.XSD_TAG, XsdSimpleContent::parse);
		parseMappers.Add(XsdSimpleContent.XS_TAG, XsdSimpleContent::parse);

		parseMappers.Add(XsdEnumeration.XSD_TAG, XsdEnumeration::parse);
		parseMappers.Add(XsdEnumeration.XS_TAG, XsdEnumeration::parse);
		parseMappers.Add(XsdFractionDigits.XSD_TAG, XsdFractionDigits::parse);
		parseMappers.Add(XsdFractionDigits.XS_TAG, XsdFractionDigits::parse);
		parseMappers.Add(XsdLength.XSD_TAG, XsdLength::parse);
		parseMappers.Add(XsdLength.XS_TAG, XsdLength::parse);
		parseMappers.Add(XsdMaxExclusive.XSD_TAG, XsdMaxExclusive::parse);
		parseMappers.Add(XsdMaxExclusive.XS_TAG, XsdMaxExclusive::parse);
		parseMappers.Add(XsdMaxInclusive.XSD_TAG, XsdMaxInclusive::parse);
		parseMappers.Add(XsdMaxInclusive.XS_TAG, XsdMaxInclusive::parse);
		parseMappers.Add(XsdMaxLength.XSD_TAG, XsdMaxLength::parse);
		parseMappers.Add(XsdMaxLength.XS_TAG, XsdMaxLength::parse);
		parseMappers.Add(XsdMinExclusive.XSD_TAG, XsdMinExclusive::parse);
		parseMappers.Add(XsdMinExclusive.XS_TAG, XsdMinExclusive::parse);
		parseMappers.Add(XsdMinInclusive.XSD_TAG, XsdMinInclusive::parse);
		parseMappers.Add(XsdMinInclusive.XS_TAG, XsdMinInclusive::parse);
		parseMappers.Add(XsdMinLength.XSD_TAG, XsdMinLength::parse);
		parseMappers.Add(XsdMinLength.XS_TAG, XsdMinLength::parse);
		parseMappers.Add(XsdPattern.XSD_TAG, XsdPattern::parse);
		parseMappers.Add(XsdPattern.XS_TAG, XsdPattern::parse);
		parseMappers.Add(XsdTotalDigits.XSD_TAG, XsdTotalDigits::parse);
		parseMappers.Add(XsdTotalDigits.XS_TAG, XsdTotalDigits::parse);
		parseMappers.Add(XsdWhiteSpace.XSD_TAG, XsdWhiteSpace::parse);
		parseMappers.Add(XsdWhiteSpace.XS_TAG, XsdWhiteSpace::parse);

		return parseMappers;
	}
}