package proholz.xsdparser;

/**
 * Represents the restrictions of the {@link XsdAnnotation} element, which can only contain {@link XsdAppInfo} and
 * {@link XsdDocumentation} as children.
 */
public class XsdAnnotationVisitor extends XsdAbstractElementVisitor {

	/**
	 * The {@link XsdAnnotation} instance which owns this {@link XsdAnnotationVisitor} instance. This way this visitor
	 * instance can perform changes in the {@link XsdAnnotation} object.
	 */
	private final XsdAnnotation owner;

	public XsdAnnotationVisitor(XsdAnnotation owner){
		super(owner);
		this.owner = owner;
	}

	@Override
	public void visit(XsdAppInfo element) {
		super.visit(element);

		owner.add(element);
	}

	@Override
	public void visit(XsdDocumentation element) {
		super.visit(element);

		owner.add(element);
	}

}