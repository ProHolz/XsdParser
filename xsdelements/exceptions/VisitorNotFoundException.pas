namespace proholz.xsdparser;

interface

type
	VisitorNotFoundException = public class(RTLException)
	public
	constructor(message: String);
	end;

implementation

constructor VisitorNotFoundException(message: String);
begin
	 inherited constructor(message);
end;

end.