namespace proholz.xsdparser;

interface

type
  XsdAnnotationVisitor = public class(XsdAbstractElementVisitor)
  private
	// *
	//      * The {@link XsdAnnotation} instance which owns this {@link XsdAnnotationVisitor} instance. This way this visitor
	//      * instance can perform changes in the {@link XsdAnnotation} object.
	//
	//
	var owner: XsdAnnotation;
  public
	constructor(aowner: XsdAnnotation);
	method visit(element: XsdAppInfo); override;
	method visit(element: XsdDocumentation); override;
  end;

implementation

constructor XsdAnnotationVisitor(aowner: XsdAnnotation);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdAnnotationVisitor.visit(element: XsdAppInfo);
begin
  inherited visit(element);
  owner.add(element);
end;

method XsdAnnotationVisitor.visit(element: XsdDocumentation);
begin
  inherited visit(element);
  owner.add(element);
end;

end.