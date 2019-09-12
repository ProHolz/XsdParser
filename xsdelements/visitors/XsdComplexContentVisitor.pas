namespace proholz.xsdparser;

interface

type
  XsdComplexContentVisitor = public class(XsdAnnotatedElementsVisitor)
  private
	// *
	//      * The {@link XsdComplexContent} instance which owns this {@link XsdComplexContentVisitor} instance. This way this
	//      * visitor instance can perform changes in the {@link XsdComplexContent} object.
	//
	//
	var owner: XsdComplexContent;
  public
	constructor(aowner: XsdComplexContent);
	method visit(element: XsdRestriction); override;
	method visit(element: XsdExtension); override;
  end;

implementation

constructor XsdComplexContentVisitor(aowner: XsdComplexContent);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdComplexContentVisitor.visit(element: XsdRestriction);
begin
  inherited visit(element);
  owner.setRestriction(ReferenceBase.createFromXsd(element));
end;

method XsdComplexContentVisitor.visit(element: XsdExtension);
begin
  inherited visit(element);
  owner.setExtension(ReferenceBase.createFromXsd(element));
end;

end.