namespace proholz.xsdparser;

interface

type
  XsdSimpleTypeVisitor = public class(XsdAnnotatedElementsVisitor)
  private
	// *
	//      * The {@link XsdSimpleType} instance which owns this {@link XsdSimpleTypeVisitor} instance. This way this visitor
	//      * instance can perform changes in the {@link XsdSimpleType} object.
	//
	//
	var owner: XsdSimpleType;
  public
	constructor(aowner: XsdSimpleType);
	method visit(element: XsdList); override;
	method visit(element: XsdUnion); override;
	method visit(element: XsdRestriction); override;
  end;

implementation

constructor XsdSimpleTypeVisitor(aowner: XsdSimpleType);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdSimpleTypeVisitor.visit(element: XsdList);
begin
  inherited visit(element);
  owner.setList(element);
end;

method XsdSimpleTypeVisitor.visit(element: XsdUnion);
begin
  inherited visit(element);
  owner.setUnion(element);
end;

method XsdSimpleTypeVisitor.visit(element: XsdRestriction);
begin
  inherited visit(element);
  owner.setRestriction(element);
end;

end.