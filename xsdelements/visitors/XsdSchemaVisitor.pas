namespace proholz.xsdparser;

interface

type
  XsdSchemaVisitor = public class(AttributesVisitor)
  private
	var owner: XsdSchema;
  public
	constructor(aowner: XsdSchema);
	method visit(element: XsdInclude); override;
	method visit(element: XsdImport); override;
	method visit(element: XsdAnnotation); override;
	method visit(element: XsdSimpleType); override;
	method visit(element: XsdComplexType); override;
	method visit(element: XsdGroup); override;
	method visit(element: XsdAttributeGroup); override;
	method visit(element: XsdElement); override;
	method visit(element: XsdAttribute); override;
  end;

implementation

constructor XsdSchemaVisitor(aowner: XsdSchema);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdSchemaVisitor.visit(element: XsdInclude);
begin
  inherited visit(element);
  owner.add(element);
end;

method XsdSchemaVisitor.visit(element: XsdImport);
begin
  inherited visit(element);
  owner.add(element);
end;

method XsdSchemaVisitor.visit(element: XsdAnnotation);
begin
  inherited visit(element);
  owner.add(element);
end;

method XsdSchemaVisitor.visit(element: XsdSimpleType);
begin
  inherited visit(element);
  owner.add(element);
end;

method XsdSchemaVisitor.visit(element: XsdComplexType);
begin
  inherited visit(element);
  owner.add(element);
end;

method XsdSchemaVisitor.visit(element: XsdGroup);
begin
  inherited visit(element);
  owner.add(element);
end;

method XsdSchemaVisitor.visit(element: XsdAttributeGroup);
begin
  inherited visit(element);
  owner.add(element);
end;

method XsdSchemaVisitor.visit(element: XsdElement);
begin
  inherited visit(element);
  owner.add(element);
end;

method XsdSchemaVisitor.visit(element: XsdAttribute);
begin
  inherited visit(element);
  owner.add(element);
end;

end.