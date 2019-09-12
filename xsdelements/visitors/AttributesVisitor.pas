namespace proholz.xsdparser;

interface

type
  AttributesVisitor = public abstract class(XsdAnnotatedElementsVisitor)
  private
	// *
	//      * The list of {@link XsdAttributeGroup} instances received by this visitor, wrapped in a {@link ReferenceBase} object.
	//
	//
	var attributeGroups: List<ReferenceBase> := new List<ReferenceBase>();
	// *
	//      * The list of {@link XsdAttribute} instances received by this visitor, wrapped in a {@link ReferenceBase} object.
	//
	//
	var attributes: List<ReferenceBase> := new List<ReferenceBase>();
  assembly
	constructor(owner: XsdAnnotatedElements);
  public
	method visit(attribute: XsdAttribute); override;
	method visit(attributeGroup: XsdAttributeGroup); override;
	method setAttributes(values: List<ReferenceBase>); virtual;
	method setAttributeGroups(values: List<ReferenceBase>); virtual;
	// *
	//      * @return All the wrapped {@link XsdAttribute} objects received by this visitor.
	method getAttributes: List<ReferenceBase>; virtual;
	// *
	//      * @return All the wrapped {@link XsdAttributeGroup} objects received by this visitor.
	method getAttributeGroups: List<ReferenceBase>; virtual;
	// *
	//      * @return All the {@link XsdAttribute} objects that are fully resolved by this visitor. The {@link XsdAttribute}
	//      * objects wrapped in {@link UnsolvedReference} objects are not returned.
	method getXsdAttributes: ISequence<XsdAttribute>; virtual;
	// *
	//              * @return All the {@link XsdAttributeGroup} objects that are fully resolved by this visitor. The
	//              * {@link XsdAttributeGroup} objects wrapped in {@link UnsolvedReference} objects are not returned.
	method getXsdAttributeGroup: ISequence<XsdAttributeGroup>; virtual;
	// *
	//                      * Tries to match the received {@link NamedConcreteElement} object, with any of the elements present either in
	//                      * {@link AttributesVisitor#attributeGroups} or {@link AttributesVisitor#attributes}. If a match occurs this method
	//                      * performs all the required actions to fully exchange the {@link UnsolvedReference} object with the element parameter.
	//                      * @param element The resolved element that will be match with the contents of this visitor in order to assert if
	//                      *                there is anything to replace.
	method replaceUnsolvedAttributes(element: NamedConcreteElement); virtual;
  end;

implementation

constructor AttributesVisitor(owner: XsdAnnotatedElements);
begin
  inherited constructor(owner);
end;

method AttributesVisitor.visit(attribute: XsdAttribute);
begin
  inherited visit(attribute);
  attributes.Add(ReferenceBase.createFromXsd(attribute));
end;

method AttributesVisitor.visit(attributeGroup: XsdAttributeGroup);
begin
  inherited visit(attributeGroup);
  attributeGroups.Add(ReferenceBase.createFromXsd(attributeGroup));
end;

method AttributesVisitor.setAttributes(values: List<ReferenceBase>);
begin
  self.attributes := values;
end;

method AttributesVisitor.setAttributeGroups(values: List<ReferenceBase>);
begin
  self.attributeGroups := values;
end;

method AttributesVisitor.getAttributes: List<ReferenceBase>;
begin
  exit attributes;
end;

method AttributesVisitor.getAttributeGroups: List<ReferenceBase>;
begin
  exit attributeGroups;
end;

method AttributesVisitor.getXsdAttributes: ISequence<XsdAttribute>;
begin
  exit attributes.Where((attribute) -> attribute is ConcreteElement).Where((attribute) -> attribute.getElement() is XsdAttribute).Select((attribute) -> XsdAttribute(attribute.getElement()));
end;

method AttributesVisitor.getXsdAttributeGroup: ISequence<XsdAttributeGroup>;
begin
  exit attributeGroups.Where((attributeGroup) -> attributeGroup is ConcreteElement).Select((attributeGroup) -> XsdAttributeGroup(attributeGroup.getElement()));
end;

method AttributesVisitor.replaceUnsolvedAttributes(element: NamedConcreteElement);
begin
  if element.getElement() is XsdAttributeGroup then begin
	var referenceBase: ReferenceBase := attributeGroups.Where((attributeGroup) -> (attributeGroup is UnsolvedReference) and XsdAbstractElement.compareReference(element, UnsolvedReference(attributeGroup))).FirstOrDefault();
	if assigned(referenceBase) then begin
	  attributeGroups.Remove(referenceBase);
	  attributeGroups.Add(element);
	  attributes.Add(element.getElement().getElements());
	  element.getElement().setParent(getOwner());
	end;

  end;
  if element.getElement() is XsdAttribute then begin
	var referenceBase: ReferenceBase := attributes.Where((attribute) -> (attribute is UnsolvedReference) and XsdAbstractElement.compareReference(element, UnsolvedReference(attribute))).FirstOrDefault();
	//  .ifPresent(referenceBase ->
	if assigned(referenceBase) then begin
	  attributes.Remove(referenceBase);
	  attributes.Add(element);
	  element.getElement().setParent(getOwner());
	end;

  end;
end;

end.