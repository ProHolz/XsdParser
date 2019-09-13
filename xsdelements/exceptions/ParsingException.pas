namespace proholz.xsdparser;

interface

type
	ParsingException = public class(RTLException)
	public
	constructor(message: String);
	end;

implementation

constructor ParsingException(message: String);
begin
	inherited constructor(message);

end;

end.