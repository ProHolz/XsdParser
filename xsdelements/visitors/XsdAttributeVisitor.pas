namespace proholz.xsdparser;

interface

type
  XsdAttributeVisitor = public class(XsdAnnotatedElementsVisitor)
  private
	// *
	//      * The {@link XsdAttribute} instance which owns this {@link XsdAttributeVisitor} instance. This way this visitor
	//      * instance can perform changes in the {@link XsdAttribute} object.
	//
	//
	var owner: XsdAttribute;
  public
	constructor(aowner: XsdAttribute);
	method visit(element: XsdSimpleType); override;
  end;

implementation

constructor XsdAttributeVisitor(aowner: XsdAttribute);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdAttributeVisitor.visit(element: XsdSimpleType);
begin
  inherited visit(element);
  owner.setSimpleType(ReferenceBase.createFromXsd(element));
end;

end.