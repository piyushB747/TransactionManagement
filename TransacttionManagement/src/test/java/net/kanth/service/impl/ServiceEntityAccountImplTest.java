package net.kanth.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import net.kanth.enums.EnumAccountType;
import net.kanth.enums.EnumCardType;
import net.kanth.payload.PayloadAccount;
import net.kanth.service.ServiceEntityAccount;


@Slf4j
@SpringBootTest
class ServiceEntityAccountImplTest {

	@Autowired
	private ServiceEntityAccount serviceEntityAccount;
	
	@Test
	void testToSaveAllAccounts() {
		
		Set<PayloadAccount> setList = new HashSet<>(Arrays.asList(
			    new PayloadAccount("221","Piyushraj Singh", "SBI BANK",EnumAccountType.CURRENT, EnumCardType.AMERICANEXPRESS,new BigDecimal("4354654")),
			    new PayloadAccount("765","Dipchand Yadav", "AXIS BANK",EnumAccountType.SAVING, EnumCardType.MASTERCARD, new BigDecimal("1000000")),
			    new PayloadAccount("321","Ravidas Thakur", "ICIC BANK",EnumAccountType.SAVING, EnumCardType.AMERICANEXPRESS, new BigDecimal("6546785")),
			    new PayloadAccount("432","Brizesh Khosla", "IDFC FIRST BANK",EnumAccountType.CURRENT, EnumCardType.VISA, new BigDecimal("655667")),
			    new PayloadAccount("864","Rohan johar", "INDUSLAND BANK",EnumAccountType.CURRENT, EnumCardType.VISA, new BigDecimal("30000"))
			));
		
		serviceEntityAccount.saveAccounts(setList).forEach(p ->{
		     log.info("Account Number {}",p)	;
		});
		
	}
	
	@Test
	void testToFetchAccountById() {
	       	log.info("Account Info {}",serviceEntityAccount.getAccountById(Long.valueOf(102)));
	}
	
}
