namespace TestApplication;

interface

uses
  proholz.xsdparser,
  RemObjects.Elements.EUnit;

type
  ResrictionTest = public class(TestBaseClass)
  private

  protected
    method Setup; override; public;
    begin
      var xsd := gettestParser('test');
    end;

 public
    method testIntRestrictions;
    method testStringRestrictions;
    method testAnnotations;
    (**
     * Tests a {@link XsdList} value with multiple restrictions.
     *)
    method testList;

    (**
     * Asserts if all the {@link Double} based restrictions, i.e. {@link XsdDoubleRestrictions}, parse their values
     * properly.
     *)

    method testDoubleRestrictions() ;

    method remainingTypesVerification;

    method testExtensionBase;

  end;

implementation


method ResrictionTest.testIntRestrictions;
begin
  var restrictedNumberOptional:= elements
  //.stream()
  .where(element -> element.getName().equals('restrictedNumber')).First();

  Assert.isNotNil(restrictedNumberOptional);

  var restrictedNumber: XsdElement := restrictedNumberOptional;

  var complexType: XsdComplexType := restrictedNumber.getXsdComplexType();

  Assert.isNotNil(complexType);

  var attributes: List<XsdAttribute> := complexType.getXsdAttributes().toList();

  Assert.IsNotNil(attributes);

  Assert.AreEqual(1, attributes.Count);

  var attribute: XsdAttribute := attributes.First;

  Assert.AreEqual('restrictedNumberAttr', attribute.getName());

  Assert.IsNil(attribute.getType());

  Assert.IsNotNil('required', attribute.getUse());

  Assert.IsNotNil('restrictedNumberId', attribute.getId());

  Assert.AreEqual('qualified', attribute.getForm());

  Assert.AreEqual('true', attribute.getFixed());

  var simpleType: XsdSimpleType := attribute.getXsdSimpleType();

  Assert.IsNotNil(simpleType);

  var restriction: XsdRestriction := simpleType.getRestriction();

  Assert.IsNotNil(restriction);

  var maxInclusive: XsdMaxInclusive := restriction.getMaxInclusive();

  var maxExclusive: XsdMaxExclusive := restriction.getMaxExclusive();

  var minInclusive: XsdMinInclusive := restriction.getMinInclusive();

  var minExclusive: XsdMinExclusive := restriction.getMinExclusive();

  var totalDigits: XsdTotalDigits := restriction.getTotalDigits();

  var fractionDigits: XsdFractionDigits := restriction.getFractionDigits();

  Assert.isNotNil(maxInclusive);

  Assert.isNotNil(maxExclusive);

  Assert.isNotNil(minInclusive);

  Assert.isNotNil(minExclusive);

  Assert.isNotNil(totalDigits);

  Assert.isNotNil(fractionDigits);

  Assert.AreEqual(100.0, maxExclusive.getValue(), 0);

  Assert.IsTrue(maxExclusive.isFixed());

  Assert.AreEqual(0.0, minExclusive.getValue(), 0);

  Assert.IsTrue(minExclusive.isFixed());

  Assert.AreEqual(99.0, maxInclusive.getValue(), 0);

  Assert.assertFalse(maxInclusive.isFixed());

  Assert.AreEqual(1.0, minInclusive.getValue(), 0);

  Assert.assertFalse(minInclusive.isFixed());

  Assert.AreEqual(2.0, fractionDigits.getValue(), 0);

  Assert.IsTrue(fractionDigits.isFixed());

  Assert.AreEqual(10.0, totalDigits.getValue(), 0);

  Assert.assertFalse(totalDigits.isFixed());



end;

method ResrictionTest.testStringRestrictions;
begin
  var restrictedStringOptional  := elements
  //.stream()
  .where(element -> element.getName().equals('restrictedString')).First;

  Assert.IsTrue(restrictedStringOptional <> nil);

  var restrictionString: XsdElement := restrictedStringOptional;

  var complexType: XsdComplexType := restrictionString.getXsdComplexType();

  Assert.isNotNil(complexType);

  var attributes: List<XsdAttribute> := complexType.getXsdAttributes().toList();

  Assert.isNotNil(attributes);

  Assert.AreEqual(1, attributes.Count);

  var attribute: XsdAttribute := attributes.First;

  Assert.AreEqual('restrictedStringAttr', attribute.getName());

  var simpleType: XsdSimpleType := attribute.getXsdSimpleType();

  Assert.isNotNil(simpleType);

  var restriction: XsdRestriction := simpleType.getRestriction();

  Assert.isNotNil(restriction);

  var xsdLength: XsdLength := restriction.getLength();

  var xsdMaxLength: XsdMaxLength := restriction.getMaxLength();

  var xsdMinLength: XsdMinLength := restriction.getMinLength();

  var xsdPattern: XsdPattern := restriction.getPattern();

  var xsdWhiteSpace: XsdWhiteSpace := restriction.getWhiteSpace();

  Assert.isNotNil(xsdLength);

  Assert.isNotNil(xsdMaxLength);

  Assert.isNotNil(xsdMinLength);

  Assert.isNotNil(xsdPattern);

  Assert.isNotNil(xsdWhiteSpace);

  Assert.AreEqual(10.0, xsdLength.getValue(), 0);

  Assert.IsTrue(xsdLength.isFixed());

  Assert.AreEqual(10.0, xsdLength.getValue(), 0);

  Assert.IsTrue(xsdLength.isFixed());

  Assert.AreEqual(10.0, xsdLength.getValue(), 0);

  Assert.IsTrue(xsdLength.isFixed());

  Assert.AreEqual('.*', xsdPattern.getValue());

  Assert.IsTrue(xsdWhiteSpace.getValue().ToString = 'preserve');

  Assert.AreEqual(xsdWhiteSpace.getValue().ToString, 'preserve');

  Assert.assertFalse(xsdWhiteSpace.isFixed());

end;

method ResrictionTest.testAnnotations;
begin
  var schema: XsdSchema := schemas.First();

  Assert.IsTrue(schema <> nil);

  var annotatedTypeOptional: XsdComplexType :=
    schema
    .getChildrenComplexTypes()
    .Where(element -> element.getName().equals('annotatedElement')).First();

  Assert.IsTrue(annotatedTypeOptional <> nil);

  var annotatedType: XsdComplexType := annotatedTypeOptional;

  var annotation: XsdAnnotation := annotatedType.getAnnotation();

  Assert.isNotNil(annotation);

  var appInfoList: List<XsdAppInfo> := annotation.getAppInfoList();

  var documentations: List<XsdDocumentation> := annotation.getDocumentations();

  Assert.isNotNil(appInfoList);

  Assert.isNotNil(documentations);

  Assert.AreEqual(1, appInfoList.Count);

  Assert.AreEqual(1, documentations.Count);

  var appInfo: XsdAppInfo := appInfoList.First;

  var documentation: XsdDocumentation := documentations.First;

  Assert.AreEqual('source', appInfo.getSource());

  Assert.AreEqual('source', documentation.getSource());

  Check.AreEqual(appInfo.getContent(), 'Some text.');

  Check.AreEqual(documentation.getContent(), 'Some documentation.');



end;

method ResrictionTest.testList;
begin
  var restrictedListOptional: XsdElement :=
  elements
  //.stream()
  .Where(element -> element.getName().equals('restrictedList')).First();

  Assert.IsTrue(restrictedListOptional <> nil);

  var restrictedListElement: XsdElement := restrictedListOptional;

  var simpleType: XsdSimpleType := restrictedListElement.getXsdSimpleType();

  Assert.isNotNil(simpleType);

  var list: XsdList := simpleType.getList();

  Assert.isNotNil(list);

  Assert.AreEqual(list.getId(), 'listId');

  var listSimpleType: XsdSimpleType := list.getXsdSimpleType();

  Assert.isNotNil(listSimpleType);

  var listRestrictions: List<XsdRestriction> := listSimpleType.getAllRestrictions();

  Assert.AreEqual(1, listRestrictions.Count);

  var restriction: XsdRestriction := listRestrictions.First;

  Assert.isNotNil(restriction);

  var length: XsdLength := restriction.getLength();

  var maxLength: XsdMaxLength := restriction.getMaxLength();

  Assert.isNotNil(length);

  Assert.AreEqual(5.0, length.getValue(), 0);

  Assert.AreEqual(5.0, maxLength.getValue(), 0);


end;

method ResrictionTest.testDoubleRestrictions;
begin
  var xsdSchemaOptional: XsdSchema := parser.getResultXsdSchemas().First();

  Assert.IsTrue(xsdSchemaOptional <> nil);

  var xsdSchema: XsdSchema := xsdSchemaOptional;

  var simpleTypeObj: XsdSimpleType :=
   xsdSchema
   .getChildrenSimpleTypes()
   .Where(simpleType -> simpleType.getName().equals('IDContatto')).First();

  Assert.IsTrue(simpleTypeObj <> nil);

  var simpleType: XsdSimpleType := simpleTypeObj;

  var restriction: XsdRestriction := simpleType.getRestriction();

  Assert.isNotNil(restriction);

  var minInclusive: XsdMinInclusive := restriction.getMinInclusive();

  var maxInclusive: XsdMaxInclusive := restriction.getMaxInclusive();

  Assert.isNotNil(minInclusive);

  Assert.isNotNil(maxInclusive);

  Assert.AreEqual(minInclusive.getValue(), 99999999999999.0);

  Assert.AreEqual(maxInclusive.getValue(),99999999999999.9);



end;

method ResrictionTest.remainingTypesVerification;
begin
  var schema: XsdSchema := schemas.First;

  var groupOptional: XsdGroup := schema.getChildrenGroups().First();

  Assert.IsTrue(groupOptional <> nil);

  var &group: XsdGroup := groupOptional;

  Assert.AreEqual('randomGroup', &group.getName());

  var all: XsdAll := &group.getChildAsAll();

  Assert.IsNil(&group.getChildAsChoice());

  Assert.isNil(&group.getChildAsSequence());

  Assert.isNotNil(all);

  var allChildren: List<XsdElement> := all.getChildrenElements().toList();

  Assert.AreEqual(1, allChildren.Count);

  var element: XsdElement := allChildren.First;

  var complexType: XsdComplexType := element.getXsdComplexType();

  Assert.isNotNil(complexType);

  var simpleContent: XsdSimpleContent := complexType.getSimpleContent();

  Assert.isNotNil(simpleContent);

  var &extension: XsdExtension := simpleContent.getXsdExtension();

  Assert.isNotNil(&extension);

  var base: XsdComplexType := &extension.getBaseAsComplexType();

  Assert.isNotNil(base);

  Assert.AreEqual('annotatedElement', base.getName());

end;

method ResrictionTest.testExtensionBase;
begin
  var schema := schemas.First();
  Assert.IsTrue(schema <> nil);

  var baseTypeExpectedOptional: XsdComplexType :=
  schema
  .getChildrenComplexTypes()
  .Where((xsdComplexType) -> xsdComplexType.getName().equals('baseType')).First();

  Assert.IsTrue(baseTypeExpectedOptional <> nil);

  var baseTypeExpected: XsdComplexType := baseTypeExpectedOptional;

  var rootOptional:= elements
  //.stream()
  .Where((element) -> element.getName().equals('root'))
  .First();

  Assert.IsTrue(rootOptional <> nil);

  var root: XsdElement := rootOptional;

  var extendedType: XsdComplexType := root.getXsdComplexType();

  Assert.IsNotNil(extendedType);
  Assert.AreEqual(extendedType.getName, 'extendedType');


  var complexContent: XsdComplexContent := extendedType.getComplexContent();

  Assert.IsNotNil(complexContent);


  var &extension: XsdExtension := complexContent.getXsdExtension();

  var baseType: XsdComplexType := &extension.getBaseAsComplexType();

  Assert.AreEqual(baseTypeExpected, baseType);

  Assert.IsNotNil(&extension);
  // Shuld have on1 child the sequenze
  Assert.AreEqual(&extension.getElements.Count, 1);




  var &sequence := &extension.getChildAsSequence();


  Assert.IsNotNil(&sequence);

  var element  := &sequence.getChildrenElements().First();

  Assert.IsTrue(element <> nil);

  Assert.AreEqual('additionElement', element.getName());




end;



end.