package proholz.xsdparser;



/**
 * ConcreteElement is a wrapper class for an {@link XsdAbstractElement} object which doesn't have a ref attribute.
 */
public class ConcreteElement extends ReferenceBase {

    ConcreteElement(XsdAbstractElement element){
        super(element);
    }

}
