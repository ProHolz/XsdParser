namespace XsdParser.Web;

type
  [Export]
  Program = public class
  public

    method HelloWorld;
    begin
      writeLn('HelloWorld');
      var el := WebAssembly.GetElementById('helloWorld');
      if el = nil then begin
        writeLn('Element by ID test is null!');
        exit;
      end;
      var t2 := WebAssembly.CreateTextNode('Hello from Elements WebAssembly!');
      el.appendChild(t2);
    end;

  end;

end.