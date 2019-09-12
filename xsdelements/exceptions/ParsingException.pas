namespace proholz.xsdparser;

interface

type
  ParsingException = public class(Exception)
  public
	constructor(message: String);
  end;

implementation

constructor ParsingException(message: String);
begin
  inherited constructor(message);
end;

end.