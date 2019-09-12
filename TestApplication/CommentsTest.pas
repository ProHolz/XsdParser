namespace TestApplication;

interface

uses
  proholz.xsdparser,
  RemObjects.Elements.EUnit;

type
  CommentsTest = public class(TestBaseClass)
  private
  protected
    method SetupTest;  override; public;
    begin
      //var xsd :=
      gettestParser('comments.xsd');
    end;
  public
    method testHierarchy;
  end;

implementation

method CommentsTest.testHierarchy;
begin
  Assert.AreEqual(elements.Count, 1);
end;



end.