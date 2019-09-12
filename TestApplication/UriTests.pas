namespace TestApplication;

interface

uses
  RemObjects.Elements.EUnit;

type
  UriTests = public class(Test)
  private
  protected
  public
    method FirstTest;
  end;

implementation

method UriTests.FirstTest;
begin
  var lUri := Uri.TryUriWithString(":EL_Namespace");
  Assert.IsNotNil(lUri);
  Check.AreEqual(lUri.ToString, ":EL_Namespace");
  var lUrn := Urn.TryUrnWithString("EL_Namespace");
  Assert.IsNotNil(lUrn);
  Assert.AreEqual(lUrn.ToString, "EL_Namespace");
end;

end.