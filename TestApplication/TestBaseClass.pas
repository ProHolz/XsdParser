namespace TestApplication;

interface

uses
  proholz.xsdparser,
  RemObjects.Elements.EUnit;

type
  TestBaseClass = public class(Test)
  private
    //const testfile : String = 'test.xsd';
  protected
     var  elements : List<XsdElement> ;
     var  schemas  : List<XsdSchema>;
     var parser : XsdParser;

    method gettestParser(const testfile : String) : XsdParser;
  public

  end;

implementation

method TestBaseClass.gettestParser(const testfile : String): XsdParser;
begin
  var fullname := Path.Combine('../../resources', Path.ChangeExtension(testfile,'.xsd'));
  if File.Exists(fullname) then
    begin
      parser := new XsdParser(fullname);
     schemas := parser.getResultXsdSchemas().toList();
     elements := parser.getResultXsdElements().toList();
     exit parser;
    end
  else
  Assert.Fail($'Testfile {testfile} not Found');
end;


end.