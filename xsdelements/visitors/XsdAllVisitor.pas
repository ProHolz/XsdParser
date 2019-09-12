namespace proholz.xsdparser;

interface

type
  XsdAllVisitor = public class(XsdAnnotatedElementsVisitor)
  private
	// *
	//      * The {@link XsdAll} instance which owns this {@link XsdAllVisitor} instance. This way this visitor instance can
	//      * perform changes in the {@link XsdAll} object.
	//
	//
	var owner: XsdAll;
  public
	constructor(aowner: XsdAll);
	method visit(element: XsdElement); override;
  end;

implementation

constructor XsdAllVisitor(aowner: XsdAll);
begin
  inherited constructor(owner);
  self.owner := aowner;
end;

method XsdAllVisitor.visit(element: XsdElement);
begin
  inherited visit(element);
  owner.addElement(element);
end;

end.