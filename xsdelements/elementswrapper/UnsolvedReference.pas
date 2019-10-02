namespace proholz.xsdparser;

interface

type
	UnsolvedReference = public class(ReferenceBase)
	private
		var ref: String;
		var isTypeRef: Boolean;
	assembly
		constructor(element: XsdNamedElements);
	public
			constructor(refType: String; element: XsdNamedElements);
			method getRef: String; virtual;
			method isTypeRef: Boolean; virtual;
			method getParent: XsdAbstractElement; virtual;
		end;

implementation

constructor UnsolvedReference(element: XsdNamedElements);
begin
	inherited constructor(element);
	self.ref := getRef(element);
	self.isTypeRef := false;
end;

constructor UnsolvedReference(refType: String; element: XsdNamedElements);
begin
	inherited constructor(element);
	self.ref := refType;
	self.isTypeRef := true;
end;

method UnsolvedReference.getRef: String;
begin
	exit ref;
end;

method UnsolvedReference.isTypeRef: Boolean;
begin
	exit isTypeRef;
end;

method UnsolvedReference.getParent: XsdAbstractElement;
begin
	exit element.getParent();
end;

end.