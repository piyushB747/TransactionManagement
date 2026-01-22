package net.kanth.enums;

public enum EnumAccountType {

	SAVING(1), CURRENT(2);

	private final int accountCode;

	private EnumAccountType(int accountCode) {
		this.accountCode = accountCode;
	}

	public int getAccountCode() {
		return accountCode;
	}

	public static EnumAccountType returnAccountType(int code) {

		for (EnumAccountType e1 : EnumAccountType.values()) {
			if (e1.getAccountCode() == code) {
				return e1;
			}
		}

		throw new RuntimeException("Account not found");

	}
}
