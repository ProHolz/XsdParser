namespace proholz.xsdparser;

interface

type
  XsdRestrictionsVisitor = public class(AttributesVisitor)
  private
	// *
	//      * The {@link XsdRestriction} instance which owns this {@link XsdRestrictionsVisitor} instance. This way this
	//      * visitor instance can perform changes in the {@link XsdRestriction} object.
	//
	//
	var owner: XsdRestriction;
  public
	constructor(aowner: XsdRestriction);
	method visit(element: XsdEnumeration); override;
	method visit(element: XsdFractionDigits); override;
	method visit(element: XsdLength); override;
	method visit(element: XsdMaxExclusive); override;
	method visit(element: XsdMaxInclusive); override;
	method visit(element: XsdMaxLength); override;
	method visit(element: XsdMinExclusive); override;
	method visit(element: XsdMinInclusive); override;
	method visit(element: XsdMinLength); override;
	method visit(element: XsdPattern); override;
	method visit(element: XsdTotalDigits); override;
	method visit(element: XsdWhiteSpace); override;
	method visit(element: XsdSimpleType); override;
  end;

implementation

constructor XsdRestrictionsVisitor(aowner: XsdRestriction);
begin
  inherited constructor(aowner);
  self.owner := aowner;
end;

method XsdRestrictionsVisitor.visit(element: XsdEnumeration);
begin
  inherited visit(element);
  owner.add(element);
end;

method XsdRestrictionsVisitor.visit(element: XsdFractionDigits);
begin
  inherited visit(element);
  owner.setFractionDigits(element);
end;

method XsdRestrictionsVisitor.visit(element: XsdLength);
begin
  inherited visit(element);
  owner.setLength(element);
end;

method XsdRestrictionsVisitor.visit(element: XsdMaxExclusive);
begin
  inherited visit(element);
  owner.setMaxExclusive(element);
end;

method XsdRestrictionsVisitor.visit(element: XsdMaxInclusive);
begin
  inherited visit(element);
  owner.setMaxInclusive(element);
end;

method XsdRestrictionsVisitor.visit(element: XsdMaxLength);
begin
  inherited visit(element);
  owner.setMaxLength(element);
end;

method XsdRestrictionsVisitor.visit(element: XsdMinExclusive);
begin
  inherited visit(element);
  owner.setMinExclusive(element);
end;

method XsdRestrictionsVisitor.visit(element: XsdMinInclusive);
begin
  inherited visit(element);
  owner.setMinInclusive(element);
end;

method XsdRestrictionsVisitor.visit(element: XsdMinLength);
begin
  inherited visit(element);
  owner.setMinLength(element);
end;

method XsdRestrictionsVisitor.visit(element: XsdPattern);
begin
  inherited visit(element);
  owner.setPattern(element);
end;

method XsdRestrictionsVisitor.visit(element: XsdTotalDigits);
begin
  inherited visit(element);
  owner.setTotalDigits(element);
end;

method XsdRestrictionsVisitor.visit(element: XsdWhiteSpace);
begin
  inherited visit(element);
  owner.setWhiteSpace(element);
end;

method XsdRestrictionsVisitor.visit(element: XsdSimpleType);
begin
  inherited visit(element);
  owner.setSimpleType(element);
end;

end.