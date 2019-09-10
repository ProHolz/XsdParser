package proholz.xsdparser;

public class XsdSchema extends XsdAnnotatedElements {

	public static final String XSD_TAG = "xsd:schema";
	public static final String XS_TAG = "xs:schema";

	/**
	 * {@link XsdSchemaVisitor} which restricts its children to {@link XsdInclude}, {@link XsdImport},
	 * {@link XsdAnnotation}, {@link XsdSimpleType}, {@link XsdComplexType}, {@link XsdGroup}, {@link XsdAttribute},
	 * {@link XsdAttributeGroup} and {@link XsdElement} instances.
	 */
	private XsdSchemaVisitor visitor = new XsdSchemaVisitor(this);

	/**
	 * Specifies if the form attribute for the current {@link XsdSchema} children attributes. The default value is
	 * "unqualified". Other possible value is "qualified".
	 */
	private FormEnum attributeFormDefault;

	/**
	 * Specifies if the form attribute for the current {@link XsdSchema} children elements. The default value is
	 * "unqualified". Other possible value is "qualified".
	 */
	private FormEnum elementFormDefault;

	/**
	 * Specifies if the block attribute for the current {@link XsdSchema} children such as {@link XsdElement} and
	 * {@link XsdComplexType}. The default value is "". Other possible value are "extension", "restriction",
	 * "substitution" and "#all".
	 */
	private BlockDefaultEnum blockDefault;

	/**
	 * Specifies if the final attribute for the current {@link XsdSchema} children such as {@link XsdElement},
	 * {@link XsdSimpleType} and {@link XsdComplexType}. The default value is "". Other possible value are "extension",
	 * "restriction", "list", "union" and "#all".
	 */
	private FinalDefaultEnum finalDefault;

	/**
	 * A URI reference of the namespace of this {@link XsdSchema} element.
	 */
	private String targetNamespace;

	/**
	 * The version of this {@link XsdSchema} element.
	 */
	private String version;

	/**
	 * A URI reference that specifies one or more namespaces for use in this {@link XsdSchema}. If no prefix is assigned,
	 * the schema components of the namespace can be used with unqualified references.
	 */
	private String xmlns;

	private Dictionary<String, NamespaceInfo> namespaces = new Dictionary<String, NamespaceInfo>();

	/**
	 * The children elements contained in this {@link XsdSchema} element.
	 */
	private List<XsdAbstractElement> elements = new List<XsdAbstractElement>();

	private XsdSchema(XsdParserCore! parser, Dictionary<String, String>! attributesMap){
		super(parser, attributesMap);

		this.attributeFormDefault = AttributeValidations.belongsToEnum(FormEnum.unqualified, attributesMap.getOrDefault(ATTRIBUTE_FORM_DEFAULT, FormEnum.unqualified.ToString()));
		this.elementFormDefault = AttributeValidations.belongsToEnum(FormEnum.unqualified, attributesMap.getOrDefault(ELEMENT_FORM_DEFAULT, FormEnum.unqualified.ToString()));
		this.blockDefault = AttributeValidations.belongsToEnum(BlockDefaultEnum.default, attributesMap.getOrDefault(BLOCK_DEFAULT, BlockDefaultEnum.default.ToString()));
		this.finalDefault = AttributeValidations.belongsToEnum(FinalDefaultEnum.default, attributesMap.getOrDefault(FINAL_DEFAULT, FinalDefaultEnum.default.ToString()));
		this.targetNamespace = attributesMap.getOrDefault(TARGET_NAMESPACE, targetNamespace);
		this.version = attributesMap.getOrDefault(VERSION, version);
		this.xmlns = attributesMap.getOrDefault(XMLNS, xmlns);

		for (String key : attributesMap.Keys){
			if (key.StartsWith(XMLNS) && !attributesMap.Item[key].Contains("http")){
				String namespaceId = key.Replace(XMLNS + ":", "");
				namespaces.Add(namespaceId, new NamespaceInfo(attributesMap.Item[key]));
			}
		}
	}

	@Override
	public XsdAbstractElementVisitor getVisitor() {
		return visitor;
	}

	@Override
	public ISequence<XsdAbstractElement> getXsdElements() {
		return elements;
	}

	@Override
	public List<ReferenceBase> getElements() {
		return elements.Select(ReferenceBase::createFromXsd).ToList();//(Collectors.toList());
	}

	public static ReferenceBase parse(XsdParserCore! parser, XmlElement node) {
		ReferenceBase xsdSchemaRef = xsdParseSkeleton(node, new XsdSchema(parser, convertNodeMap(node.get_Attributes())));
		XsdSchema xsdSchema = (XsdSchema) xsdSchemaRef.getElement();

		List<XsdImport> importsList = xsdSchema.getChildrenImports().ToList();// collect(Collectors.toList());

		Dictionary<String, String> prefixLocations = new Dictionary<String, String>();

		xsdSchema.namespaces
				 .ForEachHelper((prefix, namespaceInfo) -> {
					 XsdImport xsdImport = importsList
					 .Where(xsdImportObj -> xsdImportObj
					 .getNamespace()
					 .Equals(namespaceInfo.getName()))
					 .FirstOrDefault();

					 if (xsdImport != null){
						 prefixLocations.Add(prefix, xsdImport.getSchemaLocation());
					 } else {
						 throw new ParsingException("XsdSchema refers a namespace which was not imported.");
					 }
				 });

		xsdSchema.updatePrefixLocations(prefixLocations);

		return xsdSchemaRef;
	}

	private void updatePrefixLocations(Dictionary<String, String> prefixLocations) {
		prefixLocations.ForEachHelper((prefix, fileLocation) ->
			namespaces.Item[prefix].setFile(fileLocation)
		);
	}

	public void add(XsdInclude element) {
		elements.Add(element);
	}

	public void add(XsdImport element) {
		elements.Add(element);
	}

	public void add(XsdAnnotation element) {
		elements.Add(element);
	}

	public void add(XsdSimpleType element) {
		elements.Add(element);
	}

	public void add(XsdComplexType element) {
		elements.Add(element);
	}

	public void add(XsdGroup element) {
		elements.Add(element);
	}

	public void add(XsdAttributeGroup element) {
		elements.Add(element);
	}

	public void add(XsdElement element) {
		elements.Add(element);
	}

	public void add(XsdAttribute element) {
		elements.Add(element);
	}

	//@SuppressWarnings("unused")
	public String getAttributeFormDefault() {
		return attributeFormDefault.ToString();
	}

	//@SuppressWarnings("unused")
	public String getElementFormDefault() {
		return elementFormDefault.ToString();
	}

	//@SuppressWarnings("unused")
	public String getBlockDefault() {
		return blockDefault.ToString();
	}

	//@SuppressWarnings("unused")
	public String getFinalDefault() {
		return finalDefault.ToString();
	}

	//@SuppressWarnings("unused")
	public String getTargetNamespace() {
		return targetNamespace;
	}

	public String getVersion() {
		return version;
	}

	/**
	 * @return The children elements that are of the type {@link XsdInclude}.
	 */
	//@SuppressWarnings("unused")
	public ISequence<XsdInclude> getChildrenIncludes(){
		return getXsdElements()
				.Where(element -> element instanceof XsdInclude)
				.Select(element -> (XsdInclude) element);
	}

	/**
	 * @return The children elements that are of the type {@link XsdImport}.
	 */
	//@SuppressWarnings("unused")
	public ISequence<XsdImport> getChildrenImports(){
		return getXsdElements()
				.Where(element -> element instanceof XsdImport)
				.Select(element -> (XsdImport) element);
	}

	/**
	 * @return The children elements that are of the type {@link XsdAnnotation}.
	 */
	//@SuppressWarnings("unused")
	public ISequence<XsdAnnotation> getChildrenAnnotations(){
		return getXsdElements()
				.Where(element -> element instanceof XsdAnnotation)
				.Select(element -> (XsdAnnotation) element);
	}

	/**
	 * @return The children elements that are of the type {@link XsdSimpleType}.
	 */
	//@SuppressWarnings("unused")
	public ISequence<XsdSimpleType> getChildrenSimpleTypes(){
		return getXsdElements()
				.Where(element -> element instanceof XsdSimpleType)
				.Select(element -> (XsdSimpleType) element);
	}

	/**
	 * @return The children elements that are of the type {@link XsdComplexType}.
	 */
	//@SuppressWarnings("unused")
	public ISequence<XsdComplexType> getChildrenComplexTypes(){
		return getXsdElements()
				.Where(element -> element instanceof XsdComplexType)
				.Select(element -> (XsdComplexType) element);
	}

	/**
	 * @return The children elements that are of the type {@link XsdGroup}.
	 */
	//@SuppressWarnings("unused")
	public ISequence<XsdGroup> getChildrenGroups(){
		return getXsdElements()
				.Where(element -> element instanceof XsdGroup)
				.Select(element -> (XsdGroup) element);
	}

	/**
	 * @return The children elements that are of the type {@link XsdAttributeGroup}.
	 */
	//@SuppressWarnings("unused")
	public ISequence<XsdAttributeGroup> getChildrenAttributeGroups(){
		return getXsdElements()
				.Where(element -> element instanceof XsdAttributeGroup)
				.Select(element -> (XsdAttributeGroup) element);
	}

	/**
	 * @return The children elements that are of the type {@link XsdElement}.
	 */
	//@SuppressWarnings("unused")
	public ISequence<XsdElement> getChildrenElements(){
		return getXsdElements()
				.Where(element -> element instanceof XsdElement)
				.Select(element -> (XsdElement) element);
	}

	/**
	 * @return The children elements that are of the type {@link XsdAttribute}.
	 */
	//@SuppressWarnings("unused")
	public ISequence<XsdAttribute> getChildrenAttributes(){
		return getXsdElements()
				.Where(element -> element instanceof XsdAttribute)
				.Select(element -> (XsdAttribute) element);
	}

	public Dictionary<String, NamespaceInfo> getNamespaces() {
		return namespaces;
	}
}