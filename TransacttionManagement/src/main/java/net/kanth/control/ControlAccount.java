package net.kanth.control;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kanth.payload.PayloadAccount;
import net.kanth.service.ServiceEntityAccount;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="/api/accounts",name="accounts")
public class ControlAccount {
private ServiceEntityAccount serviceAccount;
	

	@GetMapping("/{id}")
	private ResponseEntity<?> getAccountById(@PathVariable Long id){
		return new ResponseEntity<>(serviceAccount.getAccountById(id),HttpStatus.OK);
	}
	
	@PostMapping
	private ResponseEntity<?> saveAccountEntity(@RequestBody PayloadAccount payload){
		return new ResponseEntity<>(serviceAccount.saveAccountEntity(payload),HttpStatus.CREATED);
	}
	
	@GetMapping
	private ResponseEntity<?> saveAccountEntityList(@RequestBody Set<PayloadAccount> payload){
		return new ResponseEntity<>(serviceAccount.saveAccounts(payload),HttpStatus.CREATED);
	}
	
	@GetMapping(value="/all",headers="X-API-VERSION-1")
	private ResponseEntity<?> getAllAcountV1(@RequestParam int pageNo,@RequestParam int pageSize,@RequestParam String sortBy,@RequestParam String sortDir){
		log.info("Version 1 {}",sortBy);
		return new ResponseEntity<>(serviceAccount.getAllAcounts(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	
	@GetMapping(value="/all",headers="X-API-VERSION-2")
	private ResponseEntity<?> getAllAcountV2(@RequestParam int pageNo,@RequestParam int pageSize,@RequestParam String sortBy,@RequestParam String sortDir){
		log.info("Version 2 {}",sortBy);
		return new ResponseEntity<>(serviceAccount.getAllAcounts(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	
}
