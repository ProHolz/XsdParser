namespace proholz.xsdparser;
type
  SimpleTypeFinalEnum = public enum (list, &union, restriction, all); //#all
  FinalDefaultEnum = public enum(default, extension, restriction, list, union , all); //#all
  FinalEnum = public enum(extension, restriction, all); //#all
// *
//  * An {@link Enum} with all the possible values for the whiteSpace attribute.
//

  WhiteSpaceEnum = public enum(preserve, collapse, replace);

/**
 * An {@link Enum} with all the possible values for the blockDefault attribute of {@link XsdSchema}.
 */
  BlockDefaultEnum = public enum(default, extension, restriction, substitution, all); //#all

  BlockEnum = public enum(extension, restriction, substitution, all); //#all

  ComplexTypeBlockEnum = public enum(extension, restriction, all); //#all

  FormEnum = public enum(qualified, unqualified);
  UsageEnum = public enum(required, prohibited, optional);

{$IF TOFFEE OR ISLAND}
  SimpleTypeFinalEnumExtensions = public extension record(SimpleTypeFinalEnum)
  public
    class method TryParse(value : String; out res : SimpleTypeFinalEnum) : boolean;
   begin
     If String.IsNullOrEmpty(value) then exit false;
     for temp : SimpleTypeFinalEnum := low(SimpleTypeFinalEnum) to High(SimpleTypeFinalEnum) do
       if temp.ToString.Equals(value) then begin
         res := temp;
         exit true;
       end;
     exit false;
   end;
 end;

  FinalDefaultEnumExtensions = public extension record(FinalDefaultEnum)
  public
    class method TryParse(value : String; out res : FinalDefaultEnum) : boolean;
    begin
      If String.IsNullOrEmpty(value) then exit false;
      for temp : FinalDefaultEnum := low(FinalDefaultEnum) to High(FinalDefaultEnum) do
       if temp.ToString.Equals(value) then begin
         res := temp;
         exit true;
       end;
     exit false;
    end;

  end;

  FinalEnumExtensions = public extension record(FinalEnum)
  public
      class method TryParse(value : String; out res : FinalEnum) : boolean;
      begin
        If String.IsNullOrEmpty(value) then exit false;
        for temp : FinalEnum := low(FinalEnum) to High(FinalEnum) do
          if temp.ToString.Equals(value) then begin
            res := temp;
            exit true;
          end;
        exit false;
      end;
    end;

  WhiteSpaceEnumExtensions = public extension record(WhiteSpaceEnum)
  public
    class method TryParse(value : String; out res : WhiteSpaceEnum) : boolean;
     begin
       If String.IsNullOrEmpty(value) then exit false;
       for temp : WhiteSpaceEnum := low(WhiteSpaceEnum) to High(WhiteSpaceEnum) do
         if temp.ToString.Equals(value) then begin
           res := temp;
           exit true;
         end;
       exit false;

     end;
   end;

  BlockDefaultEnumExtensions = public extension record(BlockDefaultEnum)
  public
    class method TryParse(value : String; out res : BlockDefaultEnum) : boolean;
     begin
       If String.IsNullOrEmpty(value) then exit false;
       for temp : BlockDefaultEnum := low(BlockDefaultEnum) to High(BlockDefaultEnum) do
         if temp.ToString.Equals(value) then begin
           res := temp;
           exit true;
         end;
       exit false;
     end;
    end;

  BlockEnumExtensions = public extension record(BlockEnum)
  public
    class method TryParse(value : String; out res : BlockEnum) : boolean;
     begin
       If String.IsNullOrEmpty(value) then exit false;
       for temp : BlockEnum := low(BlockEnum) to High(BlockEnum) do
         if temp.ToString.Equals(value) then begin
           res := temp;
           exit true;
         end;
       exit false;
     end;
    end;

  FormEnumExtensions = public extension record(FormEnum)
  public
    class method TryParse(value : String; out res : FormEnum) : boolean;
      begin
        If String.IsNullOrEmpty(value) then exit false;
        for temp : FormEnum := low(FormEnum) to High(FormEnum) do
          begin
          if temp.ToString.Equals(value) then begin
            res := temp;
            exit true;
          end;
        end;
        exit false;
      end;
    end;

  ComplexTypeBlockEnumExtensions = public extension record(ComplexTypeBlockEnum)
  public
    class method TryParse(value : String; out res : ComplexTypeBlockEnum) : boolean;
        begin
          If String.IsNullOrEmpty(value) then exit false;
          for temp : ComplexTypeBlockEnum := low(ComplexTypeBlockEnum) to High(ComplexTypeBlockEnum) do
            if temp.ToString.Equals(value) then begin
              res := temp;
              exit true;
            end;
          exit false;
        end;

      end;

  UsageEnumExtensions = public extension record(UsageEnum)
  public
  class method TryParse(value : String; out res : UsageEnum) : boolean;
  begin
    If String.IsNullOrEmpty(value) then exit false;
    for temp : UsageEnum := low(UsageEnum) to High(UsageEnum) do
      if temp.ToString.Equals(value) then begin
        res := temp;
        exit true;
      end;
      exit false;
  end;
end;
 {$ENDIF}
end.