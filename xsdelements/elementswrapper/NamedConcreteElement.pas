namespace proholz.xsdparser;

interface

type
  NamedConcreteElement = public class(ConcreteElement)
  private
	var name: String;
  assembly
	constructor(element: XsdNamedElements; aname: String);
  public
	method getName: String; virtual;
	method getElement: XsdAbstractElement; override;
  end;

implementation

constructor NamedConcreteElement(element: XsdNamedElements; aname: String);
begin
  inherited constructor(element);
  self.name := aname;
end;

method NamedConcreteElement.getName: String;
begin
  exit name;
end;

method NamedConcreteElement.getElement: XsdAbstractElement;
begin
  exit element;
end;

end.