package proholz.xsdparser;
/**
 * A class representing the xsd:appInfo element. This class extends form {@link XsdAnnotationChildren} since it shares
 * a few similarities with {@link XsdDocumentation}, which is the other possible children of {@link XsdAnnotation}
 * elements. For more information check {@link XsdAnnotationChildren}.
 *
 * @see <a href="https://www.w3schools.com/xml/el_appinfo.asp">xsd:appInfo element description and usage at w3c</a>
 */
public class XsdAppInfo extends XsdAnnotationChildren {

	public static final String XSD_TAG = "xsd:appinfo";
	public static final String XS_TAG = "xs:appinfo";

	private XsdAppInfo( XsdParserCore! parser,  Dictionary<String, String>! elementFieldsMapParam) {
		super(parser, elementFieldsMapParam);
	}

	@Override
	public void accept(XsdAbstractElementVisitor visitorParam) {
		super.accept(visitorParam);
		visitorParam.visit(this);
	}

	public static ReferenceBase parse( XsdParserCore! parser, XmlElement node){
		return xsdAnnotationChildrenParse(node, new XsdAppInfo(parser, convertNodeMap(node.Attributes)));
	}
}