namespace proholz.xsdparser;

interface

type
  NamespaceInfo = public class
  private
	var name: String;
	var file: String;
  public
	constructor(aname: String);
	method getName: String; virtual;
	method getFile: String; virtual;
	method setFile(afile: String); virtual;
  end;

implementation

constructor NamespaceInfo(aname: String);
begin
  self.name := aname;
end;

method NamespaceInfo.getName: String;
begin
  exit name;
end;

method NamespaceInfo.getFile: String;
begin
  exit file;
end;

method NamespaceInfo.setFile(afile: String);
begin
  self.file := afile;
end;

end.