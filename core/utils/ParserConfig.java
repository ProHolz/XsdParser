package proholz.xsdparser;



public interface ParserConfig {

	Dictionary<String, String> getXsdTypesToCodeGen();

	Dictionary<String, BiFunction<XsdParserCore, XmlElement, ReferenceBase>> getParseMappers();

}