namespace proholz.xsdparser;

interface

type
  XsdComplexTypeVisitor = public class(AttributesVisitor)
  private
	// *
	//      * The {@link XsdComplexType} instance which owns this {@link XsdComplexTypeVisitor} instance. This way this visitor
	//      * instance can perform changes in the {@link XsdComplexType} object.
	//
	//
	var owner: XsdComplexType;
  public
	constructor(aowner: XsdComplexType);
	method visit(element: XsdMultipleElements); override;
	method visit(element: XsdGroup); override;
	method visit(element: XsdComplexContent); override;
	method visit(element: XsdSimpleContent); override;
  end;

implementation

constructor XsdComplexTypeVisitor(aowner: XsdComplexType);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdComplexTypeVisitor.visit(element: XsdMultipleElements);
begin
  inherited visit(element);
  owner.setChildElement(ReferenceBase.createFromXsd(element));
end;

method XsdComplexTypeVisitor.visit(element: XsdGroup);
begin
  inherited visit(element);
  owner.setChildElement(ReferenceBase.createFromXsd(element));
end;

method XsdComplexTypeVisitor.visit(element: XsdComplexContent);
begin
  inherited visit(element);
  owner.setComplexContent(element);
end;

method XsdComplexTypeVisitor.visit(element: XsdSimpleContent);
begin
  inherited visit(element);
  owner.setSimpleContent(element);
end;

end.