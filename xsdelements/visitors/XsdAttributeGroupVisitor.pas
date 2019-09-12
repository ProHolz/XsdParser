namespace proholz.xsdparser;

interface

type
  XsdAttributeGroupVisitor = public class(XsdAnnotatedElementsVisitor)
  private
	// *
	//      * The {@link XsdAttributeGroup} instance which owns this {@link XsdAttributeGroupVisitor} instance. This way this
	//      * visitor instance can perform changes in the {@link XsdAttributeGroup} object.
	//
	//
	var owner: XsdAttributeGroup;
  public
	constructor(aowner: XsdAttributeGroup);
	method visit(element: XsdAttribute); override;
  end;

implementation

constructor XsdAttributeGroupVisitor(aowner: XsdAttributeGroup);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdAttributeGroupVisitor.visit(element: XsdAttribute);
begin
  inherited visit(element);
  owner.addAttribute(ReferenceBase.createFromXsd(element));
end;

end.