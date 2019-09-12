package proholz.xsdparser;

/**
 * This class serves as a base to every element class, i.e. {@link XsdElement}, {@link XsdAttribute}, etc.
 */
public abstract class XsdAbstractElement {

	/**
	 * A {@link Map} object containing the keys/values of the attributes that belong to the concrete element instance.
	 */
	protected Dictionary<String, String> attributesMap = new Dictionary<String, String>();


	static final String ATTRIBUTE_FORM_DEFAULT = "attribtueFormDefault";
	static final String ELEMENT_FORM_DEFAULT = "elementFormDefault";
	static final String BLOCK_DEFAULT = "blockDefault";
	static final String FINAL_DEFAULT = "finalDefault";
	static final String TARGET_NAMESPACE = "targetNamespace";
	static final String VERSION = "version";
	static final String XMLNS = "xmlns";

	static final String ID_TAG = "id";
	public static final String NAME_TAG = "name";
	static final String ABSTRACT_TAG = "abstract";
	static final String DEFAULT_ELEMENT_TAG = "defaultElement";
	protected static final String FIXED_TAG = "fixed";
	static final String TYPE_TAG = "type";
	static final String MIXED_TAG = "mixed";
	static final String BLOCK_TAG = "block";
	static final String FINAL_TAG = "final";
	static final String USE_TAG = "use";
	static final String SUBSTITUTION_GROUP_TAG = "substitutionGroup";
	static final String DEFAULT_TAG = "default";
	static final String FORM_TAG = "form";
	static final String NILLABLE_TAG = "nillable";
	static final String MIN_OCCURS_TAG = "minOccurs";
	public static final String MAX_OCCURS_TAG = "maxOccurs";
	static final String ITEM_TYPE_TAG = "itemType";
	static final String BASE_TAG = "base";
	static final String SOURCE_TAG = "source";
	static final String XML_LANG_TAG = "xml:lang";
	static final String MEMBER_TYPES_TAG = "memberTypes";
	static final String SCHEMA_LOCATION = "schemaLocation";
	static final String NAMESPACE = "namespace";
	public static final String REF_TAG = "ref";
	protected static final String VALUE_TAG = "value";

	/**
	 * The instance which contains the present element.
	 */
	XsdAbstractElement parent;

	/**
	 * The {@link XsdParserCore} instance that parsed this element.
	 */
	XsdParserCore parser;

	protected XsdAbstractElement(XsdParserCore! parser,  Dictionary<String, String>! attributesMap){
		this.parser = parser;
		this.attributesMap = attributesMap;
	}

	public Dictionary<String, String> getAttributesMap() {
		return attributesMap;
	}

	/**
	 * Obtains the visitor of a concrete {@link XsdAbstractElement} instance.
	 * @return The concrete visitor instance.
	 */
	public  XsdAbstractElementVisitor getVisitor(){
	  return null;
	};

	/**
	 * Runs verifications on each concrete element to ensure that the XSD schema rules are verified.
	 */
	public void validateSchemaRules(){ }

	/**
	 * Base method for all accept methods. It serves as a way to guarantee that every accept call assigns the parent
	 * field.
	 * @param xsdAbstractElementVisitor The visitor that is visiting the current instance.
	 */
	public void accept(XsdAbstractElementVisitor xsdAbstractElementVisitor){
		this.setParent(xsdAbstractElementVisitor.getOwner());
	}

	public List<ReferenceBase> getElements(){
		return new List<ReferenceBase>();//Collections.emptyList();
	}

	/**
	 * @return All the {@link ConcreteElement} objects present in the concrete implementation of the
	 * {@link XsdAbstractElement} class. It doesn't return the {@link UnsolvedReference} objects.
	 */
	public ISequence<XsdAbstractElement> getXsdElements(){
		List<ReferenceBase> elements = getElements();

		if (elements == null){
			return new List<XsdAbstractElement>();
		}

		return elements.Where(element -> element instanceof ConcreteElement).Select(Ref -> Ref.getElement());
	}

	/**
	 * The base code for parsing any {@link XsdAbstractElement}. All the concrete implementations of this class should
	 * call this method in order to parse its children.
	 * @param node The node from where the element will be parsed.
	 * @param element The concrete element that will be populated and returned.
	 * @return A wrapper object that contains the parsed XSD object.
	 */
	static ReferenceBase xsdParseSkeleton(XmlElement node, XsdAbstractElement element){
		XsdParserCore parser = element.getParser();
		//XmlElement child = node.getFirstChild();

	   // while (child != null) {
		for (XmlElement child : node.Elements) {
			if (child.get_NodeType() == XmlNodeType.Element) {
				String nodeName = child.FullName;

				BiFunction<XsdParserCore, XmlElement, ReferenceBase> parserFunction = XsdParserCore.getParseMappers().Item[nodeName];

				if (parserFunction != null){
					XsdAbstractElement childElement = parserFunction(parser, child).getElement();

					childElement.accept(element.getVisitor());

					childElement.validateSchemaRules();
				}
			}

	   //     child = child.getNextSibling();
		}

		ReferenceBase wrappedElement = ReferenceBase.createFromXsd(element);

		parser.addParsedElement(wrappedElement);

		return wrappedElement;
	}

	public XsdParserCore getParser() {
		return parser;
	}

	/**
	 * Converts a {@link NamedNodeMap} to a {@link Map} object. This is meant to simplify the manipulation of the
	 * information.
	 * @param nodeMap The {@link NamedNodeMap} to convert to a {@link Map} object.
	 * @return The {@link Map} object that was generated by the conversion.
	 */
	protected static Dictionary<String, String> convertNodeMap(NamedNodeMap nodeMap){
		Dictionary<String, String> attributesMapped = new Dictionary<String, String>();

		for (int i = 0; i < nodeMap.Count(); i++) {
			XmlAttribute node = nodeMap.ElementAt(i);
			attributesMapped.Add(node.LocalName, node.Value);
		}

		return attributesMapped;
	}

	 /**
	 * Converts a {@link NamedNodeMap} to a {@link Map} object. This is meant to simplify the manipulation of the
	 * information.
	 * @param nodeMap The {@link NamedNodeMap} to convert to a {@link Map} object.
	 * @return The {@link Map} object that was generated by the conversion.
	 */
	protected static Dictionary<String, String> convertToNodeMapFromNode(XmlElement node){
		Dictionary<String, String> attributesMapped = new Dictionary<String, String>();
		NamedNodeMap nodeMap = node.Attributes;

		for (int i = 0; i < nodeMap.Count(); i++) {
			XmlAttribute newnode = nodeMap.ElementAt(i);
			attributesMapped.Add(newnode.LocalName, newnode.Value);
		}

		for (int i = 0; i < node.DefinedNamespaces.Count(); i++) {
			XmlNamespace ns = node.DefinedNamespaces.ElementAt(i);
			attributesMapped.Add("xmlns:"+ns.Prefix, ns.Uri.ToString());
		}

		return attributesMapped;
	}


	/**
	 * This method iterates on the current element children and replaces any {@link UnsolvedReference} object that has a
	 * ref attribute that matches the receiving {@link NamedConcreteElement} name attribute.
	 * @param element A fully parsed element with a name that will replace an {@link UnsolvedReference} object, if a
	 *                match between the {@link NamedConcreteElement} name attribute and the {@link UnsolvedReference}
	 *                ref attribute.
	 */
	public void replaceUnsolvedElements(NamedConcreteElement element){
		List<ReferenceBase> elements = this.getElements();

		if (elements != null){
			ReferenceBase oldElement =
			elements
				.Where(referenceBase -> referenceBase instanceof UnsolvedReference)
				.Select(referenceBase -> (UnsolvedReference) referenceBase)
				.Where(unsolvedReference -> compareReference(element, unsolvedReference))
				.FirstOrDefault();

				if (oldElement != null){
					elements.Item[elements.IndexOf(oldElement)] = element;
				}
			}
	}

		public static boolean compareReference(NamedConcreteElement element, UnsolvedReference reference){
			return compareReference(element, reference.getRef());
		}

		static boolean compareReference(NamedConcreteElement element, String unsolvedRef){
			if (unsolvedRef.Contains(":")){
				unsolvedRef = unsolvedRef.Substring(unsolvedRef.IndexOf(":") + 1);
			}

			return element.getName().Equals(unsolvedRef);
		}

		/**
		 * @return The parent of the current {@link XsdAbstractElement} object.
		 */
		public XsdAbstractElement getParent() {
			return parent;
		}

		public void setParent(XsdAbstractElement parent) {
			this.parent = parent;
		}

		/**
		 * In special cases such as {@link XsdAppInfo} and {@link XsdDocumentation} the contents are a simple text node,
		 * in which case this function is more suited than using the {@link XsdAbstractElement#xsdParseSkeleton} since
		 * those types of elements can't have children nodes.
		 * @param node The {@link XmlElement} containing either a {@link XsdAppInfo} or {@link XsdDocumentation}.
		 * @return The textual value contained in the {@link XmlElement} parameter.
		 */
		static String xsdRawContentParse(XmlElement node) {
		  //  XmlElement child = node.Elements.First();
			StringBuilder stringBuilder = new StringBuilder();
			//if (node.NodeType == XmlNodeType.Text || node.NodeType == XmlNodeType.CData){
				stringBuilder.Append(node.Value.Trim());
			  //}
		   //  #todo : Check if Child parse is necessary
			for (XmlElement child : node.Elements) {
		   // while (child != null) {
				if (child.NodeType == XmlNodeType.Text || child.NodeType == XmlNodeType.CData) {
					stringBuilder.Append(child.Value.Trim());
				}

			   // child = child.getNextSibling();
			}

			return stringBuilder.ToString();
		}
	}