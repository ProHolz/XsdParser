namespace proholz.xsdparser;


interface
type
  AttributeValidations = public partial class
  private

 // assembly
  // *
  //      * Verifies if a given value is present in a given {@link Enum} type.
  //      * @param instance An instance of the concrete {@link Enum} type that is expected to contain the {@code value} received.
  //      * @param value The value that is expected to be present in the received {@link Enum} type.
  //      * @param <T> The concrete type of the {@link Enum} type.
  //      * @return The instance of the concrete {@link Enum} type that represents {@code value} in the respective {@link Enum}.

   // SimpleTypeFinalEnum
    public
     class method belongsToEnum(instance : SimpleTypeFinalEnum; const value : String) : SimpleTypeFinalEnum;
     class method belongsToEnum(instance: WhiteSpaceEnum; const value: String): WhiteSpaceEnum;
    class method belongsToEnum(instance: BlockDefaultEnum; const value: String): BlockDefaultEnum;
    class method belongsToEnum(instance: BlockEnum; const value: String): BlockEnum;

    class method belongsToEnum(instance: ComplexTypeBlockEnum; const value: String): ComplexTypeBlockEnum;
    class method belongsToEnum(instance: FinalDefaultEnum; const value: String): FinalDefaultEnum;
    class method belongsToEnum(instance: FinalEnum; const value: String): FinalEnum;
    class method belongsToEnum(instance: FormEnum; const value: String): FormEnum;
    class method belongsToEnum(instance: UsageEnum; const value: String): UsageEnum;



end;

implementation

class method AttributeValidations.belongsToEnum(instance: SimpleTypeFinalEnum; const value: String): SimpleTypeFinalEnum;
begin
  if SimpleTypeFinalEnum.TryParse(value, out result) then exit;
  if String.Compare(value, '#all') = 0 then exit SimpleTypeFinalEnum.all;
  exit instance;
end;

class method AttributeValidations.belongsToEnum(instance: WhiteSpaceEnum; const value: String): WhiteSpaceEnum;
begin
  if WhiteSpaceEnum.TryParse(value, out result) then exit;
//  if String.Compare(value, '#all') = 0 then exit xsdReader.SimpleTypeFinalEnum.all;
  exit instance;
end;

class method AttributeValidations.belongsToEnum(instance: BlockDefaultEnum; const value: String): BlockDefaultEnum;
begin
  if BlockDefaultEnum.TryParse(value, out result) then exit;
  if String.Compare(value, '#all') = 0 then exit BlockDefaultEnum.all;
 exit instance;
end;

class method AttributeValidations.belongsToEnum(instance: BlockEnum; const value: String): BlockEnum;
begin
  if BlockEnum.TryParse(value, out result) then exit;
  if String.Compare(value, '#all') = 0 then exit BlockEnum.all;
  exit instance;
end;

class method AttributeValidations.belongsToEnum(instance: ComplexTypeBlockEnum; const value: String): ComplexTypeBlockEnum;
begin
  if ComplexTypeBlockEnum.TryParse(value, out result) then exit;
  if String.Compare(value, '#all') = 0 then exit ComplexTypeBlockEnum.all;
 exit instance;
end;

class method AttributeValidations.belongsToEnum(instance: FinalDefaultEnum; const value: String): FinalDefaultEnum;
begin
  if FinalDefaultEnum.TryParse(value, out result) then exit;
  if String.Compare(value, '#all') = 0 then exit FinalDefaultEnum.all;
 exit instance;
end;

class method AttributeValidations.belongsToEnum(instance: FinalEnum; const value: String): FinalEnum;
begin
  if FinalEnum.TryParse(value, out result) then exit;
  if String.Compare(value, '#all') = 0 then exit FinalEnum.all;
exit instance;
end;

class method AttributeValidations.belongsToEnum(instance: FormEnum; const value: String): FormEnum;
begin
  if FormEnum.TryParse(value, out result) then exit;
  exit instance;
end;

class method AttributeValidations.belongsToEnum(instance: UsageEnum; const value: String): UsageEnum;
begin
  if UsageEnum.TryParse(value, out result) then exit;
   exit instance;
end;

end.