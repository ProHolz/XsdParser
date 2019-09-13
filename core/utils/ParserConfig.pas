namespace proholz.xsdparser;

interface

type

	ParseMappersDictionary = Dictionary<String, BiFunction<XsdParserCore,XmlElement,ReferenceBase>>;

	ParserConfig = public interface
	method getXsdTypesToCodeGen: Dictionary<String,String>;
		method getParseMappers: ParseMappersDictionary;

	end;

implementation

end.