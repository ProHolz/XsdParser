namespace proholz.xsdparser;

interface

type
  ParserConfig = public interface
	method getXsdTypesToCodeGen: Dictionary<String,String>;
	method getParseMappers: Dictionary<String,BiFunction<XsdParserCore,XmlElement,ReferenceBase>>;
  end;

implementation

end.