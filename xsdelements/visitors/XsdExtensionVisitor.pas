namespace proholz.xsdparser;

interface

type
  XsdExtensionVisitor = public class(AttributesVisitor)
  private
	// *
	//      * The {@link XsdExtension} instance which owns this {@link XsdExtensionVisitor} instance. This way this visitor
	//      * instance can perform changes in the {@link XsdExtension} object.
	//
	//
	var owner: XsdExtension;
  public
	constructor(aowner: XsdExtension);
	method visit(element: XsdMultipleElements); override;
	method visit(element: XsdGroup); override;
  end;

implementation

constructor XsdExtensionVisitor(aowner: XsdExtension);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdExtensionVisitor.visit(element: XsdMultipleElements);
begin
  inherited visit(element);
  owner.setChildElement(ReferenceBase.createFromXsd(element));
end;

method XsdExtensionVisitor.visit(element: XsdGroup);
begin
  inherited visit(element);
  owner.setChildElement(ReferenceBase.createFromXsd(element));
end;

end.