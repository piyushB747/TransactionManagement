package net.kanth.payload;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kanth.enums.EnumAccountType;
import net.kanth.enums.EnumCardType;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PayloadAccount {
	
	private String accountNo;
	
	private String accountName;
	
	private String bankName;
	
	private EnumAccountType accountType;
	
	private EnumCardType cardType;
	
	private BigDecimal amount;
	
}
