namespace proholz.xsdparser;

type
  NamedNodeMap = not nullable sequence of XmlAttribute;

  FileNotFoundException = Exception;
  MalformedURLException = Exception;
  SAXException = Exception;
  ParserConfigurationException = Exception;
  InvalidParameterException = Exception;

// Blocks
  BiFunction<T,U,C> =  public block(left : T; right : U):C;
 {$if TOFFEE OR ISLAND}
  Action<T,U> = public block (Obj: T; Value :U);
{$endif}

type
  ISequence_Extensions<T> = public extension class (ISequence<T>)
  public
    method isEmpty : Boolean;
    begin
      exit not self.Any();
    end;

     {$IF ISLAND OR TOFFEE}
    method ElementAt(&index : Integer) : T;
    begin
      for each el in self index i do
        if i = &index then exit el;
      raise new RTLException('Index out of Range in ElementAt');
    end;
    {$ENDIF}

  end;

  IList_Extensions<T> = public extension class (List<T>)
  public

    {$IF ISLAND}
    method ElementAt(&index : Integer) : T;
    begin
      exit self.Item[&index];
    end;
    {$ENDIF}

    method removeIf(Match: Predicate<T>);
    begin
      for i : Integer := self.Count -1 downto 0 do
        if Match(self.ElementAt(i)) then
          self.RemoveAt(i);
    end;
  end;


  IDictionary_Extensions<T,U> = public extension class (Dictionary<T,U>)
  private
  protected
  public
    method getOrDefault(aT : T; aU :  U): U;
    begin
      result :=   Item[aT];
      if result = nil then
        exit aU;
    end;

    method ForEachHelper(Action: Action<T,U>);
    begin
      for each temp in self do Action(temp.Key, temp.Value);
    end;

  end;

 {$if TOFFEE}
extension method ISequence<T>.ToDictionary<TKey, TValue>(
aKeySelector: not nullable block (Item : T): TKey;
aValueSelector: not nullable block (Item : T): TValue): not nullable Dictionary<TKey, TValue>; public;
begin
  result := new Dictionary<TKey, TValue>;
  for each el in self do
    result.Add(aKeySelector(el), aValueSelector(el));
end;

{$endif}
// Global to remove.....

   method hasDifferentValue( o1 , o2 :XsdStringRestrictions) : Boolean;
   begin
     exit XsdStringRestrictions.hasDifferentValue(o1, o2);
   end;


   method hasDifferentValue( o1 , o2 :XsdDoubleRestrictions) : Boolean;
   begin
     exit XsdDoubleRestrictions.hasDifferentValue(o1, o2);
   end;


   method hasDifferentValue( o1 , o2 :XsdIntegerRestrictions) : Boolean;
   begin
     exit XsdIntegerRestrictions.hasDifferentValue(o1, o2);
   end;



   method hasDifferentValue( o1 , o2 :XsdWhiteSpace) : Boolean;
   begin
     exit XsdWhiteSpace.hasDifferentValue(o1, o2);
   end;


end.