namespace proholz.xsdparser;

interface

type
  XsdAbstractElementVisitor = public class
  protected
    owner : XsdAbstractElement;
  public

    constructor( aowner : XsdAbstractElement);
    begin
      self.owner := aowner;
    end;

    method visit(element: XsdAll); virtual;
    begin
      visit(XsdMultipleElements( element));
    end;
    method visit(element: XsdAttribute); virtual;
    begin
      visit(XsdNamedElements( element));
    end;

      method visit(element: XsdAttributeGroup); virtual;
      begin
        visit(XsdNamedElements (element));
      end;

      method visit(element: XsdChoice); virtual;
      begin
        visit(XsdMultipleElements( element));
      end;
      method visit(element: XsdComplexType); virtual; empty;

      method visit(element: XsdElement); virtual;
      begin
        visit(XsdNamedElements( element));
      end;

      method visit(element: XsdGroup); virtual;
      begin
        visit(XsdNamedElements( element));
      end;

      method visit(element: XsdSequence); virtual;
      begin
        visit(XsdMultipleElements( element));
      end;
      method visit(element: XsdMultipleElements); virtual; empty;

      method visit(element: XsdNamedElements); virtual;
      begin
        var refBase := ReferenceBase.createFromXsd(element);

        if (refBase is  UnsolvedReference) then
        begin
          element.getParser().addUnsolvedReference(UnsolvedReference( refBase));
        end;

     end;

      method visit(element: XsdSimpleType); virtual; empty;
      method visit(element: XsdRestriction); virtual; empty;
      method visit(element: XsdList); virtual; empty;
      method visit(element: XsdUnion); virtual; empty;
      method visit(element: XsdEnumeration); virtual; empty;
      method visit(element: XsdFractionDigits); virtual; empty;
      method visit(element: XsdLength); virtual; empty;
      method visit(element: XsdMaxExclusive); virtual; empty;
      method visit(element: XsdMaxInclusive); virtual; empty;
      method visit(element: XsdMaxLength); virtual; empty;
      method visit(element: XsdMinExclusive); virtual; empty;
      method visit(element: XsdMinInclusive); virtual; empty;
      method visit(element: XsdMinLength); virtual; empty;
      method visit(element: XsdPattern); virtual; empty;
      method visit(element: XsdTotalDigits); virtual; empty;
      method visit(element: XsdWhiteSpace); virtual; empty;
      method visit(element: XsdExtension); virtual; empty;
      method visit(element: XsdComplexContent); virtual; empty;
      method visit(element: XsdSimpleContent); virtual; empty;
      method visit(element: XsdDocumentation); virtual; empty;
      method visit(element: XsdAppInfo); virtual; empty;
      method visit(xsdAnnotation: XsdAnnotation); virtual; empty;
      method visit(xsdImport: XsdImport); virtual; empty;
      method visit(xsdInclude: XsdInclude); virtual; empty;


      method getOwner: XsdAbstractElement;  final;
      begin
        exit owner;
      end;
    end;

implementation

end.