package proholz.xsdparser;

/**
 * Exception that is thrown every time that an {@link XsdAbstractElement#accept} method is accessed when the concrete
 * element shouldn't receive visits.
 */
public class VisitorNotFoundException extends Exception{

    public VisitorNotFoundException(String message){
        super(message);
    }

}