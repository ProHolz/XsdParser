namespace proholz.xsdparser;

interface

type
  XsdSimpleContentVisitor = public class(XsdAnnotatedElementsVisitor)
  private
	// *
	//      * The {@link XsdSimpleContent} instance which owns this {@link XsdSimpleContentVisitor} instance. This way this
	//      * visitor instance can perform changes in the {@link XsdSimpleContent} object.
	//
	//
	var owner: XsdSimpleContent;
  public
	constructor(aowner: XsdSimpleContent);
	method visit(element: XsdRestriction); override;
	method visit(element: XsdExtension); override;
  end;

implementation

constructor XsdSimpleContentVisitor(aowner: XsdSimpleContent);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdSimpleContentVisitor.visit(element: XsdRestriction);
begin
  inherited visit(element);
  owner.setRestriction(ReferenceBase.createFromXsd(element));
end;

method XsdSimpleContentVisitor.visit(element: XsdExtension);
begin
  inherited visit(element);
  owner.setExtension(ReferenceBase.createFromXsd(element));
end;

end.