namespace proholz.xsdparser;

interface

type
  UnsolvedReferenceItem = public class
  private
	// *
	//      * A {@link UnsolvedReference} object that wasn't solved in the parsing process. This happened because its referred
	//      * element isn't present in the files that were parsed.
	//
	//
	var unsolvedReference: UnsolvedReference;
	// *
	//      * A list of parents which indicate all the places where the {@link UnsolvedReference} object was used, which cause
	//      * every element present in this list to not be fully correct.
	//
	//
	var parents: List<XsdAbstractElement>;
  public
	constructor(aunsolvedReference: UnsolvedReference);
	method getUnsolvedReference: UnsolvedReference; virtual;
	method getParents: List<XsdAbstractElement>; virtual;
  end;

implementation

constructor UnsolvedReferenceItem(aunsolvedReference: UnsolvedReference);
begin
  self.unsolvedReference := aunsolvedReference;
  self.parents := new List<XsdAbstractElement>();
  self.parents.Add(unsolvedReference.getParent());
end;

method UnsolvedReferenceItem.getUnsolvedReference: UnsolvedReference;
begin
  exit unsolvedReference;
end;

method UnsolvedReferenceItem.getParents: List<XsdAbstractElement>;
begin
  exit parents;
end;

end.