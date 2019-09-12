namespace proholz.xsdparser;

interface

type
  XsdGroupVisitor = public class(XsdAnnotatedElementsVisitor)
  private
	// *
	//      * The {@link XsdGroup} instance which owns this {@link XsdGroupVisitor} instance. This way this visitor instance
	//      * can perform changes in the {@link XsdGroup} object.
	//
	//
	var owner: XsdGroup;
  public
	constructor(aowner: XsdGroup);
	method visit(element: XsdMultipleElements); override;
  end;

implementation

constructor XsdGroupVisitor(aowner: XsdGroup);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdGroupVisitor.visit(element: XsdMultipleElements);
begin
  inherited visit(element);
  owner.setChildElement(element);
end;

end.