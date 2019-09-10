namespace XsdParser.Net;

uses
  proholz.xsdparser;

type
  Program = class
  public

    class method Main(args: array of String): Int32;
    begin
      // add your own code here
      writeLn('The magic happens here.');
      var filename := '/Volumes/HD2/BTL/btlx_11.xsd';
     // var filename := '/Volumes/HD2/BTL/x3d-3.3.xsd';
      var Xsd := new  XsdParser(filename);

      if Xsd.getUnsolvedReferences.Count > 0 then
      begin
        writeLn('There are unsolved References:');
        for each item in Xsd.getUnsolvedReferences do  begin
          writeLn(item.getUnsolvedReference.getRef);
        end;
      end
      else
       writeLn('All solved');


      writeLn('solved : ');

      for each item in Xsd.getResultXsdSchemas do  begin
       writeLn(item.getTargetNamespace);
       for each name in item.getNamespaces() do
         writeLn(name.Key);

       for each ele in item.getChildrenComplexTypes do begin
         writeLn(ele.getRawName);
       end;


      end;

    end;

  end;

end.