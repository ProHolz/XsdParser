namespace proholz.xsdparser;

interface

type
  XsdAnnotatedElementsVisitor = public class(XsdAbstractElementVisitor)
  private
	// *
	//      * The {@link XsdAnnotatedElements} instance which owns this {@link XsdAnnotatedElementsVisitor} instance. This way
	//      * this visitor instance can perform changes in the {@link XsdAnnotatedElements} objects.
	//
	//
	var owner: XsdAnnotatedElements;
  public
	constructor(aowner: XsdAnnotatedElements);
	method visit(element: XsdAnnotation); override;
  end;

implementation

constructor XsdAnnotatedElementsVisitor(aowner: XsdAnnotatedElements);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdAnnotatedElementsVisitor.visit(element: XsdAnnotation);
begin
  inherited visit(element);
  owner.setAnnotation(element);
end;

end.