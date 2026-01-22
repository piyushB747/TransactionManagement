package net.kanth.control;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kanth.payload.PayloadPayment;
import net.kanth.service.ServiceEntityPayment;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="/api/payment")
public class ControlPayment {

	private ServiceEntityPayment serviceEntityPayment;
	
	@PostMapping("/begaintransaction")
	public ResponseEntity<?> begainTransaction(@RequestBody PayloadPayment payload){
		log.info("Transaction Begains for {}",payload.getTo());
		return new ResponseEntity<>(serviceEntityPayment.begainTransactionV2(payload.getTransactionAmount(),payload.getFrom().toString(),payload.getTo().toString()),HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllTransactions(){
		log.info("Fetching all transactions! ");
		return new ResponseEntity<>(serviceEntityPayment.fetchAllPayment(),HttpStatus.OK);
	}
	
	
}
