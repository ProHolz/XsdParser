namespace proholz.xsdparser;

interface

type
	ReferenceBase = public abstract class
	private
		class method getName(element: XsdAbstractElement): String;
		// *
		//      * @param element The element that contains the attributes.
		//      * @param nodeName The attribute name that will be searched.
		//      * @return The value of the attribute contained in element with the name nodeName.
		class method getNodeValue(element: XsdAbstractElement; nodeName: String): String;
	assembly
		constructor(aelement: XsdAbstractElement);
		class method getRef(element: XsdAbstractElement): String;
	assembly or protected
			var element: XsdAbstractElement;
		public
			method getElement: XsdAbstractElement; virtual;
			// *
			//      * This method creates a ReferenceBase object that serves as a wrapper to {@link XsdAbstractElement} objects.
			//      * If a {@link XsdAbstractElement} has a ref attribute it results in a {@link UnsolvedReference} object. If it
			//      * doesn't have a ref attribute and has a name attribute it's a {@link NamedConcreteElement}. If it isn't a
			//      * {@link UnsolvedReference} or a {@link NamedConcreteElement} then it's a {@link ConcreteElement}.
			//      * @param element The element which will be "wrapped".
			//      * @return A wrapper object for the element received.
			class method createFromXsd(element: XsdAbstractElement): ReferenceBase;
		end;

implementation

constructor ReferenceBase(aelement: XsdAbstractElement);
begin
	self.element := aelement;
end;

method ReferenceBase.getElement: XsdAbstractElement;
begin
	exit element;
end;

class method ReferenceBase.createFromXsd(element: XsdAbstractElement): ReferenceBase;
begin
	var ref: String := getRef(element);
	var name: String := getName(element);
	if not (element is XsdNamedElements) then begin
		exit new ConcreteElement(element);
	end;
	if ref = nil then begin
		if name = nil then begin
			exit new ConcreteElement(element);
		end
		else begin
			exit new NamedConcreteElement(XsdNamedElements(element), name);
		end;
	end
		else begin
			exit new UnsolvedReference(XsdNamedElements(element));
		end;
end;

class method ReferenceBase.getName(element: XsdAbstractElement): String;
begin
	exit getNodeValue(element, XsdAbstractElement.NAME_TAG);
end;

class method ReferenceBase.getRef(element: XsdAbstractElement): String;
begin
	exit getNodeValue(element, XsdAbstractElement.REF_TAG);
end;

class method ReferenceBase.getNodeValue(element: XsdAbstractElement; nodeName: String): String;
begin
	exit element.getAttributesMap().getOrDefault(nodeName, nil);
end;

end.