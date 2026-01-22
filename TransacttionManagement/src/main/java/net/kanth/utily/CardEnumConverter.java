package net.kanth.utily;

import jakarta.persistence.AttributeConverter;
import net.kanth.enums.EnumCardType;

public class CardEnumConverter implements AttributeConverter<EnumCardType, Integer>{

	@Override
	public Integer convertToDatabaseColumn(EnumCardType attribute) {
		return (attribute==null)?0:attribute.getCode();
	}

	@Override
	public EnumCardType convertToEntityAttribute(Integer dbData) {
         return (dbData==null || dbData ==0 )?null:EnumCardType.fromCode(dbData);
	}

}
