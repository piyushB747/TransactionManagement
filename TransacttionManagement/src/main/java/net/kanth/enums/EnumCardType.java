package net.kanth.enums;

public enum EnumCardType {

	MASTERCARD(01), VISA(02), AMERICANEXPRESS(03), RUPAY(04);

	private final int code;

	private EnumCardType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static EnumCardType fromCode(int code) {
		for (EnumCardType type : EnumCardType.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid code: " + code);
	}

}
