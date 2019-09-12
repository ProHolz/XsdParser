namespace proholz.xsdparser;

interface

type
  XsdElementVisitor = public class(XsdAnnotatedElementsVisitor)
  private
	// *
	//      * The {@link XsdElement} instance which owns this {@link XsdElementVisitor} instance. This way this visitor instance
	//      * can perform changes in the {@link XsdElement} object.
	//
	//
	var owner: XsdElement;
  public
	constructor(aowner: XsdElement);
	method visit(element: XsdComplexType); override;
	method visit(element: XsdSimpleType); override;
  end;

implementation

constructor XsdElementVisitor(aowner: XsdElement);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdElementVisitor.visit(element: XsdComplexType);
begin
  inherited visit(element);
  owner.setComplexType(ReferenceBase.createFromXsd(element));
end;

method XsdElementVisitor.visit(element: XsdSimpleType);
begin
  inherited visit(element);
  owner.setSimpleType(ReferenceBase.createFromXsd(element));
end;

end.