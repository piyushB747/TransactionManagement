package net.kanth.service;

import java.util.List;
import java.util.Set;

import net.kanth.payload.PayloadAccount;

public interface ServiceEntityAccount {
	
	PayloadAccount getAccountById(Long id);
	
	List<String> saveAccounts(Set<PayloadAccount> lst);

	Object saveAccountEntity(PayloadAccount payload);

	List<PayloadAccount> getAllAcounts(int pageNo,int pageSize,String sortBy,String sortDir);
}
