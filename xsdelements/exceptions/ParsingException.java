package proholz.xsdparser;

/**
 * An exception that is thrown whenever the parsing process encounters a error in the values present in the XSD file.
 * The exception message contains valuable information to find and correct the error in the file.
 */
public class ParsingException extends Exception {

    public ParsingException(String message){
        super(message);
    }

}