namespace TestApplication;
uses
RemObjects.Elements.EUnit;
type
  AssertExtension = public extension class(BaseAsserts)
  private
  protected
  public
    method assertNotNull(value : Object); inline;
    begin
      IsNotNil(value);
    end;

    method assertTrue(value : Boolean);
    begin
      IsTrue(value);
    end;

    method assertFalse(value : Boolean);
    begin
      IsFalse(value);
    end;

    method assertEquals(Expected: Double; Actual: Double; Precision: Double);
    begin
       AreEqual(Actual, Expected, Precision);

    end;

    method assertEquals(Expected: Integer; Actual: Integer);
    begin
      AreEqual(Actual, Expected);
    end;

    method assertEquals(Expected: String; Actual: String);
    begin
      AreEqual(Actual, Expected);
    end;

  end;

end.