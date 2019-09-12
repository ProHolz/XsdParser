namespace proholz.xsdparser;

interface

type
  XsdListVisitor = public class(XsdAnnotatedElementsVisitor)
  private
	// *
	//      * The {@link XsdList} instance which owns this {@link XsdListVisitor} instance. This way this visitor instance can
	//      * perform changes in the {@link XsdList} object.
	//
	//
	var owner: XsdList;
  public
	constructor(aowner: XsdList);
	method visit(element: XsdSimpleType); override;
  end;

implementation

constructor XsdListVisitor(aowner: XsdList);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdListVisitor.visit(element: XsdSimpleType);
begin
  inherited visit(element);
  if assigned(owner.getItemType()) then begin
	raise new ParsingException(XsdList.XSD_TAG + ' element: The element cannot have both the itemType attribute and a ' + XsdSimpleType.XSD_TAG + ' element as content at the same time.');
  end;
  owner.setSimpleType(element);
end;

end.