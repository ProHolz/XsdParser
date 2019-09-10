namespace TestApplication;


interface
uses
  proholz.xsdparser,
  RemObjects.Elements.EUnit;

type
  HtmlParseTest = public class(TestBaseClass)
  private
    const parserResults = new List<ParserResult>;
    const parserNonPartedResults = new List<ParserResult>;
    const parserPartedResults = new List<ParserResult>;
    // *
    //      * @return Obtains the filePath of the file associated with this test class.
    class method getFilePath(fileName: String): String;

    class constructor;
    begin
      var html5: ParserResult := new ParserResult(getFilePath('html_5.xsd'));
      var partedHtml5: ParserResult := new ParserResult(getFilePath('html_5_types.xsd'));

      Var l1 := html5.getElements.Count;
      Var l2 := partedHtml5.getElements.Count;

      parserResults.add(html5);
      parserResults.add(partedHtml5);

      parserNonPartedResults.add(html5);

      parserPartedResults.add(partedHtml5);

    end;



  public

    method testSchemaGetMethods;
    // *
    //      * Verifies the excepted element count.

    method testElementCount;
    // *
    //      * Tests if the first element, which should be the html as all the expected contents.

    method testFirstElementContents;
    // *
    //      * Tests the first element attribute count against the expected count.

    method testFirstElementAttributes;
    // *
    //      * Verifies if there is any unexpected unsolved references in the parsing.

    method testUnsolvedReferences;
    // *
    //      * Verifies the contents of a {@link XsdSimpleType} object against the expected values.

    method testSimpleTypes;
    // *
    //      * Verifies if all the html_5 html5Elements have a attribute with the name 'class'.

    method testClassAttribute;
    // *
    //      * Verifies if there is an attributeGroup named classAttributeGroup that is the parent of all the existing attributeGroups.

    method testClassParent;
  end;

  ParserResult = assembly class
  private
    var elements: List<XsdElement>;
    var schemas: List<XsdSchema>;
    var unsolved: List<UnsolvedReferenceItem>;
  assembly
    constructor(fileName: String);

    method getElements: List<XsdElement>; virtual;
    method getSchemas: List<XsdSchema>; virtual;
    method getUnsolved: List<UnsolvedReferenceItem>; virtual;
  end;

implementation

method HtmlParseTest.testSchemaGetMethods;
begin
  for each parserResult: ParserResult in parserNonPartedResults do begin
    var schema: XsdSchema := parserResult.getSchemas().First;
    Check.AreEqual(104, schema.getChildrenElements().count());
    Check.AreEqual(5, schema.getChildrenSimpleTypes().count());
    Check.AreEqual(1, schema.getChildrenAnnotations().count());
    Check.AreEqual(11, schema.getChildrenAttributeGroups().count());
    Check.AreEqual(0, schema.getChildrenAttributes().count());
    Check.AreEqual(2, schema.getChildrenComplexTypes().count());
    Check.AreEqual(8, schema.getChildrenGroups().count());
    Check.AreEqual(0, schema.getChildrenImports().count());
    Check.AreEqual(0, schema.getChildrenIncludes().count());
  end;

  for each parserRes: ParserResult in parserPartedResults do begin
    var schema0: XsdSchema := parserRes.getSchemas().First;
    Assert.AreEqual(0, schema0.getChildrenElements().count());
    Assert.AreEqual(5, schema0.getChildrenSimpleTypes().count());
    Assert.AreEqual(1, schema0.getChildrenAnnotations().count());
    Assert.AreEqual(11, schema0.getChildrenAttributeGroups().count());
    Assert.AreEqual(0, schema0.getChildrenAttributes().count());
    Assert.AreEqual(2, schema0.getChildrenComplexTypes().count());
    Assert.AreEqual(8, schema0.getChildrenGroups().count());
    Assert.AreEqual(0, schema0.getChildrenImports().count());
    Assert.AreEqual(1, schema0.getChildrenIncludes().count());
    var schema1: XsdSchema := parserRes.getSchemas().ElementAt(1);
    Assert.AreEqual(7, schema1.getChildrenElements().count());
    Assert.AreEqual(0, schema1.getChildrenSimpleTypes().count());
    Assert.AreEqual(0, schema1.getChildrenAnnotations().count());
    Assert.AreEqual(0, schema1.getChildrenAttributeGroups().count());
    Assert.AreEqual(0, schema1.getChildrenAttributes().count());
    Assert.AreEqual(0, schema1.getChildrenComplexTypes().count());
    Assert.AreEqual(0, schema1.getChildrenGroups().count());
    Assert.AreEqual(0, schema1.getChildrenImports().count());
    Assert.AreEqual(1, schema1.getChildrenIncludes().count());
  end;
end;

method HtmlParseTest.testElementCount;
begin

  for each parserRes: ParserResult in parserResults index i do begin
   case i of
    0 : Check.AreEqual(parserRes.getElements().Count(), 104);
    1 : Check.AreEqual(parserRes.getElements().Count(), 7);
    //Check.AreEqual(parserRes.getElements().Count(), 0);
end;
   end;

  for each parserRes: ParserResult in parserNonPartedResults do begin

    Check.AreEqual(parserRes.getElements().Count(), 104);
   // Check.AreEqual(parserRes.getElements().Count(), 104);

    Check.AreEqual(parserRes.getSchemas().Count(), 1);
  end;
  for each parserRes: ParserResult in parserPartedResults index i do begin
   case i  of
   0 : begin Check.AreEqual(parserRes.getElements().Count(), 7);
    Check.AreEqual(parserRes.getSchemas().Count(), 2);
    end;
  end;
end;
end;

method HtmlParseTest.testFirstElementContents;
begin
  for each parserResult: ParserResult in parserResults do begin
    var htmlElement: XsdElement := parserResult.getElements().First;
    Assert.AreEqual('html', htmlElement.getName());
    Assert.AreEqual(1, Integer(htmlElement.getMinOccurs()));
    Assert.AreEqual('1', htmlElement.getMaxOccurs());
    var firstElementChild: XsdComplexType := htmlElement.getXsdComplexType();
    Assert.AreEqual(firstElementChild.getXsdChildElement().GetType(), typeOf(XsdChoice));
    var complexTypeChild: XsdChoice := firstElementChild.getChildAsChoice();
    Assert.IsNil(firstElementChild.getChildAsAll());
    Assert.IsNil(firstElementChild.getChildAsGroup());
    Assert.IsNil(firstElementChild.getChildAsSequence());
    var choiceElements: List<XsdElement> := complexTypeChild.getChildrenElements().toList();
    Assert.AreEqual(2, choiceElements.Count());
    var child1: XsdElement := choiceElements.ElementAt(0);
    var child2: XsdElement := choiceElements.ElementAt(1);
    Assert.AreEqual('body', child1.getName());
    Assert.AreEqual('head', child2.getName());
  end;
end;

method HtmlParseTest.testFirstElementAttributes;
begin
 // for each parserRes: ParserResult in parserResults do begin
    var htmlElement: XsdElement :=  parserResults.ElementAt(0).getElements().ElementAt(0);
    Assert.AreEqual('html', htmlElement.getName());
    var firstElementChild: XsdComplexType := htmlElement.getXsdComplexType();
    var elementAttributes: List<XsdAttribute> := firstElementChild.getXsdAttributes().toList();
    Assert.AreEqual(elementAttributes.Count, 1);

end;

method HtmlParseTest.testUnsolvedReferences;
begin

end;

method HtmlParseTest.testSimpleTypes;
begin
  for each parserResult: ParserResult in parserResults do begin
    var htmlElement: XsdElement := parserResult.getElements().ElementAt(5);
    Assert.AreEqual('meta', htmlElement.getName());
    var metaChild: XsdComplexType := htmlElement.getXsdComplexType();
    var attributeOptional: XsdAttribute :=
     metaChild
     .getXsdAttributes()
     .Where(attribute1 -> attribute1.getName().equals('http_equiv')).First();

    Assert.IsTrue(attributeOptional <> nil);
    var attribute: XsdAttribute := attributeOptional;
    Assert.AreEqual(true, assigned(attribute.getXsdSimpleType()));
    var simpleType: XsdSimpleType := attribute.getXsdSimpleType();
    Assert.IsNil(simpleType.getRestriction());
    Assert.IsNil(simpleType.getList());
    Assert.assertNotNull(simpleType.getUnion());
    var &union: XsdUnion := simpleType.getUnion();
    Assert.AreEqual(2, &union.getUnionElements().Count());
    var innerSimpleType1: XsdSimpleType := &union.getUnionElements().ElementAt(0);
    Assert.assertNotNull(innerSimpleType1.getRestriction());
    Assert.IsNil(innerSimpleType1.getList());
    Assert.IsNil(innerSimpleType1.getUnion());
    var restriction: XsdRestriction := innerSimpleType1.getRestriction();
    var enumeration: List<XsdEnumeration> := restriction.getEnumeration();
    Assert.AreEqual(4, enumeration.Count());
    Assert.AreEqual('content-language', enumeration.ElementAt(0).getValue());
    Assert.AreEqual('content-type', enumeration.ElementAt(1).getValue());
    Assert.AreEqual('default-style', enumeration.ElementAt(2).getValue());
    Assert.AreEqual('refresh', enumeration.ElementAt(3).getValue());
    Assert.IsNil(restriction.getFractionDigits());
    Assert.IsNil(restriction.getLength());
    Assert.IsNil(restriction.getMaxExclusive());
    Assert.IsNil(restriction.getMaxInclusive());
    Assert.IsNil(restriction.getMaxLength());
    Assert.IsNil(restriction.getMinExclusive());
    Assert.IsNil(restriction.getMinInclusive());
    Assert.IsNil(restriction.getMinLength());
    Assert.IsNil(restriction.getPattern());
    Assert.IsNil(restriction.getTotalDigits());
    Assert.IsNil(restriction.getWhiteSpace());
  end;
end;

method HtmlParseTest.testClassAttribute;
begin
  for each parserResult: ParserResult in parserResults do begin
    parserResult
    .getElements()
    .forEach(element -> Assert.IsTrue(element
                                      .getXsdComplexType()
                                      .getXsdAttributeGroup()
                                      .any(attributeGroup ->
                                             attributeGroup
                                               .getAllAttributes()
                                               .any(attribute -> attribute.getName().equals('class'))
                                          )
                                      )
            );
  end;
end;

method HtmlParseTest.testClassParent;
begin
  for each parserRes: ParserResult in parserResults do begin
    var classAttribute: XsdAttribute :=
      parserRes
      .getElements().First
       .getXsdComplexType()
        .getXsdAttributes()
        .Where(attribute -> assigned(attribute.getName()) and attribute.getName().equals('class')).First;
    Assert.IsTrue(classAttribute <> nil);
    var classAttributeXsd: XsdAttribute := classAttribute;
    Assert.AreEqual('classAttributeGroup', XsdAttributeGroup(classAttributeXsd.getParent()).getName());
  end;
end;

class method HtmlParseTest.getFilePath(fileName: String): String;
begin
  var fullname := Path.Combine('../../resources', Path.ChangeExtension(fileName,'.xsd'));
  exit fullname;
end;



constructor ParserResult(fileName: String);
begin
  var parser: XsdParserCore := new XsdParser(fileName);
  elements := parser.getResultXsdElements().toList();
  schemas := parser.getResultXsdSchemas().toList();
  unsolved := parser.getUnsolvedReferences().ToList;
end;



method ParserResult.getElements: List<XsdElement>;
begin
  exit elements;
end;

method ParserResult.getSchemas: List<XsdSchema>;
begin
  exit schemas;
end;

method ParserResult.getUnsolved: List<UnsolvedReferenceItem>;
begin
  exit unsolved;
end;

end.