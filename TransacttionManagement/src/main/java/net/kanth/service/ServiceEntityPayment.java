package net.kanth.service;

import java.math.BigDecimal;
import java.util.List;

import net.kanth.entity.EntityPayment;

public interface ServiceEntityPayment {

	
	
	List<EntityPayment> fetchAllPayment();
	Object begainTransactionV1(BigDecimal amount,Long fromAccount,Long toAccount);
	Object begainTransactionV2(BigDecimal amount,String fromAccount,String toAccount);
}
