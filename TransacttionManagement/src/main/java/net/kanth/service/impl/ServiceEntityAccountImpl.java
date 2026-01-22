package net.kanth.service.impl;

import net.kanth.entity.EntityAccount;
import net.kanth.entity.EntityLedger;
import net.kanth.enums.LedgerType;
import net.kanth.payload.PayloadAccount;
import net.kanth.repository.RepoEntityAccount;
import net.kanth.repository.RepoEntityLedger;
import net.kanth.service.ServiceEntityAccount;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@AllArgsConstructor
@Service
public class ServiceEntityAccountImpl implements ServiceEntityAccount {
	
	private ModelMapper modelMapper;
	private RepoEntityAccount repoEntityAccount;
	private RepoEntityLedger repoEntityLedger;

	@Override
	public List<String> saveAccounts(Set<PayloadAccount> lst) {

		
		lst.forEach(p ->{
			long newAccountNumber;
			boolean exists;		
			do {
				newAccountNumber = ThreadLocalRandom.current().nextLong(5_000_000L, 10_000_000L);
				exists = repoEntityAccount.findAccountByAccountNo(newAccountNumber) > 0;
			}while(exists);
			p.setAccountNo(newAccountNumber+"");
		});
		
		Set<EntityAccount> lstEntityAccount = lst.stream().map(p -> modelMapper.map(p, EntityAccount.class))
				.collect(Collectors.toSet());
		
		
		return repoEntityAccount.saveAll(lstEntityAccount).stream().map(p -> p.getAccountNo())
				.collect(Collectors.toList());
	}

	@Override
	public PayloadAccount getAccountById(Long id) {
		
		EntityAccount e1 = repoEntityAccount.findByAccountNo(id.toString()) .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
		return modelMapper.map(e1, PayloadAccount.class);
	}

	@Transactional
	@Override
	public Object saveAccountEntity(PayloadAccount payload) {
		
		long newAccountNumber;
		boolean exists;		
		do {
			newAccountNumber = ThreadLocalRandom.current().nextLong(5_000_000L, 10_000_000L);
			exists = repoEntityAccount.findAccountByAccountNo(newAccountNumber) > 0;
		}while(exists);
		
		payload.setAccountNo(String.valueOf(newAccountNumber));
		
		EntityLedger openingCredit = new EntityLedger();
		openingCredit.setAccountNo(String.valueOf(newAccountNumber));
		openingCredit.setAmount(payload.getAmount()); // positive
		openingCredit.setType(LedgerType.CREDIT);
		openingCredit.setCounterPartyAccount("OPENING_BALANCE");

		repoEntityLedger.save(openingCredit);
		return modelMapper.map(repoEntityAccount.save(modelMapper.map(payload, EntityAccount.class)), PayloadAccount.class);
	}

	@Override
	public List<PayloadAccount> getAllAcounts(int pageNo,int pageSize,String sortBy,String sortDir) {

		
		Sort sort =  sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
				Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
		Page<EntityAccount> page = repoEntityAccount.findAll(pageable);
		List<EntityAccount> lstObj = page.getContent();
	
		return lstObj.stream().map(p -> modelMapper.map(p, PayloadAccount.class)).collect(Collectors.toList());
	}

}
