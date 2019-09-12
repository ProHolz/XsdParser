namespace proholz.xsdparser;

interface

type
  VisitorNotFoundException = public class(Exception)
  public
	constructor(message: String);
  end;

implementation

constructor VisitorNotFoundException(message: String);
begin
  inherited constructor(message);
end;

end.