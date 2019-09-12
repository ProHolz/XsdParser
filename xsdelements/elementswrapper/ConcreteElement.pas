namespace proholz.xsdparser;

interface

type
  ConcreteElement = public class(ReferenceBase)
  assembly
	constructor(element: XsdAbstractElement);
  end;

implementation

constructor ConcreteElement(element: XsdAbstractElement);
begin
  inherited constructor(element);
end;

end.