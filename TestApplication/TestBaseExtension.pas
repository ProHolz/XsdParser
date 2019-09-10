namespace TestApplication;

interface

uses
  proholz.xsdparser,
  RemObjects.Elements.EUnit;

type
  TestBaseExtension = public class(TestBaseClass)

  protected
    method Setup; override; public;
    begin
      //var xsd :=
      gettestParser('testBase');
    end;
  public
    method testExtensionBase;
  end;

implementation

method TestBaseExtension.testExtensionBase;
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

  var fchild := &extension.getXsdChildElement;
  Assert.IsNotNil(fchild);
  Assert.isTrue(fchild is XsdSequence, $'Expected XsdSequence got: {fchild.ToString}');

  var &sequence := &extension.getChildAsSequence();
  Assert.IsNotNil(&sequence);

  var element  := &sequence.getChildrenElements().First();

  Assert.IsTrue(element <> nil);

  Assert.AreEqual('additionElement', element.getName());

end;


end.