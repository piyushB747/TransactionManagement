package net.kanth.utily;

import jakarta.persistence.AttributeConverter;
import net.kanth.enums.EnumAccountType;

public class AccountEnumConverter implements AttributeConverter<EnumAccountType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(EnumAccountType attribute) {
		return (attribute == null) ? 0 : attribute.getAccountCode();
	}

	@Override
	public EnumAccountType convertToEntityAttribute(Integer dbData) {
		return (dbData == null || dbData == 0) ? null : EnumAccountType.returnAccountType(dbData);
	}

}
