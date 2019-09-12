namespace proholz.xsdparser;

interface

type
  XsdUnionVisitor = public class(XsdAnnotatedElementsVisitor)
  private
	// *
	//      * The {@link XsdUnion} instance which owns this {@link XsdUnionVisitor} instance. This way this visitor instance
	//      * can perform changes in the {@link XsdUnion} object.
	//
	//
	var owner: XsdUnion;
  public
	constructor(aowner: XsdUnion);
	method visit(element: XsdSimpleType); override;
  end;

implementation

constructor XsdUnionVisitor(aowner: XsdUnion);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdUnionVisitor.visit(element: XsdSimpleType);
begin
  inherited visit(element);
  owner.add(element);
end;

end.