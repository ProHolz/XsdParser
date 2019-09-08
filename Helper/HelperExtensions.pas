namespace proholz.xsdparser;

type
//  Node = XmlElement;

  iStream<T> = ISequence<T>;
  Stream<T> = ISequence<T>;//List<T>;

  NamedNodeMap = not nullable sequence of XmlElement;

  BiFunction<T,U,C> =  public block(left : T; right : U):C;


  FileNotFoundException = Exception;
  MalformedURLException = Exception;
  SAXException = Exception;
  ParserConfigurationException = Exception;

type
  ISequence_Extensions<T> = public extension class (ISequence<T>)
  private
  protected
  public
    method stream: not nullable ISequence<T>;
    begin
      result :=    Select(i -> i as T).ToList() as not nullable;
    end;

    method Size : Integer;
    begin
      exit self.Count;
    end;

    method filter(Match: Predicate<T>): ISequence<T>;
    begin
      exit self.Where(Match);
    end;

    method isEmpty : Boolean;
    begin
      exit self.count = 0;
    end;

    method map() : ISequence<T>;
    begin
      exit self;
    end;

    method flatmap() : ISequence<T>;
    begin
      exit self;
    end;

  end;

  IList_Extensions<T> = public extension class (List<T>)
   public
    method isPresent : Boolean;
    begin
      exit self.Count = 0;
    end;

    method add(Value : T) : T;
    begin
      exit self.Add(Value);
    end;

    method put(Value : T) : T;
    begin
      exit self.Add(Value);
    end;

    method map() : ISequence<T>;
    begin
      exit self;
    end;

    method stream: not nullable ISequence<T>;
    begin
      result :=    Select(i -> i as T).ToList() as not nullable;
    end;

    method flatmap() : ISequence<T>;
    begin
      exit self;
    end;

    method get(index : Integer): T;
    begin
      result :=   Item[index];
    end;

    //class method stream(Value : T) : ISequence<T>;
    //begin
      //exit nil;
    //end;

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

    method get(aT : T): U;
    begin
      result :=   Item[aT];
    end;

    method isPresent : Boolean;
    begin
      exit self.Count = 0;
    end;

    method put(Key : T; Value : U);
    begin
       self.Add(Key, Value);
    end;

    method values: not nullable sequence of U;
    begin
      exit self.Values;
    end;

 end;

  INode_Extensions = public extension class (XmlElement)
  private
 protected
 public
   method getNodeName : String;
   begin
     exit self.LocalName;
   end;
 end;

  IString_Extensions = public extension class (RemObjects.Elements.RTL.String)
  private
  protected
  public
   method &equals(const Value : String) : Boolean;
   begin
     exit Value = self;
   end;


  end;

 Optional<T> = nullable T;


end.