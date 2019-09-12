namespace proholz.xsdparser;

interface

type
  XsdSequenceVisitor = public class(XsdAnnotatedElementsVisitor)
  private
	// *
	//      * The {@link XsdSequence} instance which owns this {@link XsdSequenceVisitor} instance. This way this visitor instance
	//      * can perform changes in the {@link XsdSequence} object.
	//
	//
	var owner: XsdSequence;
  public
	constructor(aowner: XsdSequence);
	method visit(element: XsdElement); override;
	method visit(element: XsdGroup); override;
	method visit(element: XsdChoice); override;
	method visit(element: XsdSequence); override;
  end;

implementation

constructor XsdSequenceVisitor(aowner: XsdSequence);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdSequenceVisitor.visit(element: XsdElement);
begin
  inherited visit(element);
  owner.addElement(element);
end;

method XsdSequenceVisitor.visit(element: XsdGroup);
begin
  inherited visit(element);
  owner.addElement(element);
end;

method XsdSequenceVisitor.visit(element: XsdChoice);
begin
  inherited visit(element);
  owner.addElement(element);
end;

method XsdSequenceVisitor.visit(element: XsdSequence);
begin
  inherited visit(element);
  owner.addElement(element);
end;

end.