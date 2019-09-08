package proholz.xsdparser;



/**
 * An {@link Enum} with all the possible values for the blockDefault attribute of {@link XsdSchema}.
 */
public enum BlockDefaultEnum implements XsdEnum<BlockDefaultEnum> {

    DEFAULT (""),
    EXTENSION ("extension"),
    RESTRICTION ("restriction"),
    SUBSTITUTION("substitution"),
    ALL ("#all");

    private final String value;

    BlockDefaultEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String getVariableName() {
        return "blockDefault";
    }

    @Override
    public BlockDefaultEnum[] getValues() {
        return BlockDefaultEnum.values();
    }

    @Override
    public List<String> getSupportedValues() {
        return Arrays.asList(DEFAULT.getValue(),
                             EXTENSION.getValue(),
                             RESTRICTION.getValue(),
                             SUBSTITUTION.getValue(),
                             ALL.getValue());
    }
}
