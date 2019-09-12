namespace proholz.xsdparser;

interface

type
  XsdChoiceVisitor = public class(XsdAnnotatedElementsVisitor)
  private
	// *
	//      * The {@link XsdChoice} instance which owns this {@link XsdChoiceVisitor} instance. This way this visitor instance
	//      * can perform changes in the {@link XsdChoice} object.
	//
	//
	var owner: XsdChoice;
  public
	constructor(aowner: XsdChoice);
	method visit(element: XsdElement); override;
	method visit(element: XsdGroup); override;
	method visit(element: XsdChoice); override;
	method visit(element: XsdSequence); override;
  end;

implementation

constructor XsdChoiceVisitor(aowner: XsdChoice);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdChoiceVisitor.visit(element: XsdElement);
begin
  inherited visit(element);
  owner.addElement(element);
end;

method XsdChoiceVisitor.visit(element: XsdGroup);
begin
  inherited visit(element);
  owner.addElement(element);
end;

method XsdChoiceVisitor.visit(element: XsdChoice);
begin
  inherited visit(element);
  owner.addElement(element);
end;

method XsdChoiceVisitor.visit(element: XsdSequence);
begin
  inherited visit(element);
  owner.addElement(element);
end;

end.