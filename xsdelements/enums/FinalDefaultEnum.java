﻿package proholz.xsdparser;
/**
 * An {@link Enum} with all the possible values for the finalDefault attribute of {@link XsdSchema}.
 */
public enum FinalDefaultEnum implements XsdEnum<FinalDefaultEnum> {

	DEFAULT (""),
	EXTENSION ("extension"),
	RESTRICTION ("restriction"),
	LIST("list"),
	UNION("union"),
	ALL ("#all");

	public static FinalDefaultEnum instance = FinalDefaultEnum.DEFAULT;

	private final String value;

	FinalDefaultEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String getVariableName() {
		return "finalDefault";
	}

	@Override
	public FinalDefaultEnum[] getValues() {
		return FinalDefaultEnum.values();
	}

	@Override
	public List<String> getSupportedValues() {
		return Arrays.asList(DEFAULT.getValue(),
							 EXTENSION.getValue(),
							 RESTRICTION.getValue(),
							 LIST.getValue(),
							 UNION.getValue(),
							 ALL.getValue());
	}
}