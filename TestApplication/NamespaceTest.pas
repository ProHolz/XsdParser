namespace TestApplication;

interface

uses
  proholz.xsdparser,
  RemObjects.Elements.EUnit;

type
  NamespaceTest = public class(TestBaseClass)
  private



  protected
    method SetupTest;  override; public;
    begin
      //var xsd :=
      gettestParser('ns1.xsd');
    end;
  public
    method TestSchemas;
    method testHierarchy;

  end;

implementation

method NamespaceTest.testHierarchy;
begin

  var motherOfBtns: XsdElement :=
    elements
     //.stream()
     .Where(element -> element.getName().equals('MotherOfBtns')).First();

  Assert.IsTrue(motherOfBtns <> nil);

  var complexType: XsdComplexType := motherOfBtns.getXsdComplexType();

  Assert.IsNotNil(complexType);

  var &sequence: XsdSequence := complexType.getChildAsSequence();

  Assert.IsNotNil(&sequence);

  var sequenceMembers: List<XsdElement> := &sequence.getChildrenElements().toList();

  var member1: XsdElement := sequenceMembers.ElementAt(0);

  var member2: XsdElement := sequenceMembers.ElementAt(1);

  var member3: XsdElement := sequenceMembers.ElementAt(2);

  Assert.IsNotNil(member1);

  Assert.IsNotNil(member2);

  Assert.IsNotNil(member3);

  var complexType1: XsdComplexType := member1.getXsdComplexType();

  var complexType2: XsdComplexType := member2.getXsdComplexType();

  var complexType3: XsdComplexType := member3.getXsdComplexType();

  Assert.IsNotNil(complexType1);

  Assert.IsNotNil(complexType2);

  Assert.IsNotNil(complexType3);

  var attributes1: List<XsdAttribute> := complexType1.getXsdAttributes().toList();

  var attributes2: List<XsdAttribute> := complexType2.getXsdAttributes().toList();

  var attributes3: List<XsdAttribute> := complexType3:getXsdAttributes():toList();

  Assert.AreEqual(1, attributes1.Count);

  Assert.AreEqual(1, attributes2.Count);

  Assert.AreEqual(1, attributes3.Count);

  Check.AreEqual(attributes1.ElementAt(0).getName(), 'attr1');

  Check.AreEqual(attributes2.ElementAt(0).getName(), 'attr2');

  Assert.AreEqual(attributes3.ElementAt(0).getName(), 'attr3');


end;




method NamespaceTest.TestSchemas;
begin
  Assert.AreEqual(schemas.Count, 4);
  for each schema in schemas index i do
    begin
      case i   of
        0 : begin
             Assert.AreEqual(Path.GetFileNameWithoutExtension( schema.getFilename), 'ns1');
             var n := schema.getNamespaces;
             Assert.AreEqual(n.Count, 2);
             Assert.AreEqual(schema.getXmlns, nil);
            end;
        1 :begin
          Assert.AreEqual(Path.GetFileNameWithoutExtension( schema.getFilename), 'ns1_part2');
          var n := schema.getNamespaces;
          Assert.AreEqual(n.Count, 0);
          Assert.AreEqual(schema.getXmlns, nil);
        end;
        2:begin
          Assert.AreEqual(Path.GetFileNameWithoutExtension( schema.getFilename), 'ns2');
          var n := schema.getNamespaces;
          Assert.AreEqual(schema.getXmlns, nil);
          Assert.AreEqual(n.Count, 0);
        end;
        3:begin
          Assert.AreEqual(Path.GetFileNameWithoutExtension( schema.getFilename), 'ns3');
          var n := schema.getNamespaces;
          Assert.AreEqual(schema.getXmlns, nil);
          Assert.AreEqual(n.Count, 0);
        end;
      end;
    end;

end;



end.