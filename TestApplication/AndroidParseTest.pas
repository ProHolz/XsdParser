namespace TestApplication;

interface

uses
  proholz.xsdparser,
  RemObjects.Elements.EUnit;

type
  AndroidParseTest = public class(TestBaseClass)
  private
  protected
    method SetupTest;  override; public;
    begin
      //var xsd :=
      gettestParser('android.xsd');
    end;

  public
    // *
//      * Asserts if the hierarchy is being parsed correctly. The hierarchy is implemented with the base
//      * field.
//      * Example: If element A has a {@link XsdExtension} with his base with element B that means that
//      * element A extends element B.
//
    method testHierarchy;

  end;

implementation

method AndroidParseTest.testHierarchy;
begin
  var relativeLayoutOptional: XsdElement :=
  elements
  .Where(element -> element.getName().equals('RelativeLayout'))
  .FirstOrDefault();

  Assert.IsTrue(relativeLayoutOptional <> nil);
  var relativeLayout: XsdElement := relativeLayoutOptional;
  var relativeLayoutComplexType: XsdComplexType := relativeLayout.getXsdComplexType();
  Assert.IsNotNil(relativeLayoutComplexType);
  var relativeLayoutComplexContent: XsdComplexContent := relativeLayoutComplexType.getComplexContent();
  Assert.IsNotNil(relativeLayoutComplexContent);
  var relativeLayoutExtension: XsdExtension := relativeLayoutComplexContent.getXsdExtension();
  var viewGroupType: XsdComplexType := relativeLayoutExtension.getBaseAsComplexType();
  Assert.IsNotNil(viewGroupType);
  var viewGroupComplexContent: XsdComplexContent := viewGroupType.getComplexContent();
  Assert.IsNotNil(viewGroupComplexContent);
  var viewGroupExtension: XsdExtension := viewGroupComplexContent.getXsdExtension();
  var view: XsdComplexType := viewGroupExtension.getBaseAsComplexType();
  Assert.IsNotNil(view);
  Assert.AreEqual('View', view.getName());
end;


end.