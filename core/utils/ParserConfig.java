package proholz.xsdparser;



public interface ParserConfig {

	Dictionary<String, String> getXsdTypesToJava();

	Dictionary<String, BiFunction<XsdParserCore, XmlElement, ReferenceBase>> getParseMappers();

}