namespace TestApplication;

interface

uses
  proholz.xsdparser,
  RemObjects.Elements.EUnit;

type

(**
 * Each test represents an example of errors that might be present in a XSD file and test for the expected exception.
 *)
  XsdLanguageRestrictionsTest = public class(TestBaseClass)
  private
  protected

  public

    /**
     * The parsed file has an invalid attribute, the top level xsd:element element shouldn't have a ref attribute,
     * an exception is expected.
     */

    method testLanguageRestriction1;
    begin
      Assert.Throws(->
        gettestParser('language_restriction_1.xsd')

      , typeOf(ParsingException)
      );

    end;

     (**
     * The value passed in the minOccurs attribute should be a non negative integer, therefore the parsing throws
     * a parsing exception.
     *)
    method testLanguageRestriction2;
    begin
      Assert.Throws(->
        gettestParser('language_restriction_2.xsd')

      , typeOf(ParsingException)
      );

    end;
  /**
     * The value passed in the form attribute should belong to the {@link FormEnum}. Since it doesn't belong a parsing
     * exception is expected.
     */
    method testLanguageRestriction3;
    begin
      Assert.Throws(->
        gettestParser('language_restriction_3.xsd')

      , typeOf(ParsingException)
      );

    end;

  end;

implementation

end.