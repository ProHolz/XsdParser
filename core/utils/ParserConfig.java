package proholz.xsdparser;



public interface ParserConfig {

    Map<String, String> getXsdTypesToJava();

    Map<String, BiFunction<XsdParserCore, Node, ReferenceBase>> getParseMappers();

}
