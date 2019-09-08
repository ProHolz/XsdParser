package proholz.xsdparser;
/**
 * A class representing the xsd:list element.
 *
 * @see <a href="https://www.w3schools.com/xml/el_list.asp">xsd:list description and usage at w3c</a>
 */
public class XsdList extends XsdAnnotatedElements {

    public static final String XSD_TAG = "xsd:list";
    public static final String XS_TAG = "xs:list";

    /**
     * {@link XsdListVisitor} instance which restricts his children to {@link XsdSimpleType} instances.
     */
    private XsdListVisitor visitor = new XsdListVisitor(this);

    /**
     * The {@link XsdSimpleType} instance that states the type of the elements that belong to this {@link XsdList}
     * instance. This value shouldn't be present if there is a {@link XsdList#itemType} present.
     */
    private XsdSimpleType simpleType;

    /**
     * The itemType defines the built-it type or the name of a present {@link XsdSimpleType} instance that represent
     * the type of the elements that belong to this {@link XsdList}. This value shouldn't be present if there is a
     * {@link XsdList#simpleType} is present.
     */
    private String itemType;

    private XsdList(XsdParserCore! parser, Dictionary<String, String>! attributesMap) {
        super(parser, attributesMap);

        this.itemType = attributesMap.getOrDefault(ITEM_TYPE_TAG, itemType);
    }

    @Override
    public XsdListVisitor getVisitor() {
        return visitor;
    }

    @Override
    public void accept(XsdAbstractElementVisitor visitorParam) {
        super.accept(visitorParam);
        visitorParam.visit(this);
    }

    public static ReferenceBase parse(XsdParserCore! parser, XmlElement node){
        return xsdParseSkeleton(node, new XsdList(parser, convertNodeMap(node.get_Attributes())));
    }

    public XsdSimpleType getXsdSimpleType() {
        return simpleType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setSimpleType(XsdSimpleType simpleType) {
        this.simpleType = simpleType;
    }
}