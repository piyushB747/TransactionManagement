package net.kanth.payload;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class PayloadPayment {

	private BigDecimal transactionAmount;
	private Long to;
	private Long from;
	
}
