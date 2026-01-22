package net.kanth.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kanth.entity.EntityAccount;
import net.kanth.entity.EntityLedger;
import net.kanth.entity.EntityPayment;
import net.kanth.enums.LedgerType;
import net.kanth.repository.RepoEntityAccount;
import net.kanth.repository.RepoEntityLedger;
import net.kanth.repository.RepoEntityPayment;
import net.kanth.service.ServiceEntityAuditLogs;
import net.kanth.service.ServiceEntityPayment;

@Slf4j
@AllArgsConstructor
@Service
public class ServiceEntityPaymentImpl implements ServiceEntityPayment {

	private RepoEntityAccount repoEntityAccount;
	private RepoEntityPayment repoEntityPayment;
	private ServiceEntityAuditLogs auditService;
	private RepoEntityLedger repoLedger;

	@Transactional(isolation = Isolation.READ_COMMITTED,    propagation = Propagation.REQUIRED)
	@Override
	public Object begainTransactionV2(BigDecimal amount, String fromAccount, String toAccount) {
		try {

			if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("Invalid amount");
			}

			if (fromAccount.equals(toAccount)) {
				throw new IllegalArgumentException("From and To account cannot be same");
			}
			
			// 🔑 Deadlock-safe lock order
	        String firstLock  = fromAccount.compareTo(toAccount) < 0 ? fromAccount : toAccount;
	        String secondLock = fromAccount.compareTo(toAccount) < 0 ? toAccount : fromAccount;

	        EntityAccount acc1 = repoEntityAccount.findByAccountNo(firstLock)
	                .orElseThrow(() -> new RuntimeException("Account not found: " + firstLock));

	        EntityAccount acc2 = repoEntityAccount.findByAccountNo(secondLock)
	                .orElseThrow(() -> new RuntimeException("Account not found: " + secondLock));

	        EntityAccount from = fromAccount.equals(acc1.getAccountNo()) ? acc1 : acc2;
	        EntityAccount to   = toAccount.equals(acc1.getAccountNo()) ? acc1 : acc2;

			BigDecimal fromBalance = repoLedger.getBalance(fromAccount);

			if (fromBalance.compareTo(amount) < 0) {
				throw new RuntimeException("Insufficient balance");
			}
			
			EntityLedger debit = new EntityLedger();
	        debit.setAccountNo(fromAccount);
	        debit.setAmount(amount.negate());
	        debit.setType(LedgerType.DEBIT);
	        debit.setCounterPartyAccount(toAccount);
	       
	        
	        EntityLedger credit = new EntityLedger();
	        credit.setAccountNo(toAccount);
	        credit.setAmount(amount);
	        credit.setType(LedgerType.CREDIT);
	        credit.setCounterPartyAccount(fromAccount);
	        

	        repoLedger.save(debit);
	        repoLedger.save(credit);

	        // 5. Update cached balance (derived, NOT truth)
	        BigDecimal newFromBalance = fromBalance.subtract(amount);
	        BigDecimal newToBalance   = repoLedger.getBalance(toAccount).add(amount);

	        from.setAmount(newFromBalance);
	        to.setAmount(newToBalance);
	        
            EntityPayment payment = new EntityPayment();
            payment.setAccountFrom(from);
            payment.setAccountTo(to);
            payment.setTransactionAmount(amount);
            payment.setAccountNoFrom(Long.valueOf(fromAccount));
            payment.setAccountNoTo(Long.valueOf(toAccount));
            payment.setCard(from.getCardType());
            EntityPayment saved = repoEntityPayment.save(payment);
            
            auditService.saveAuditSuccess(Long.valueOf(fromAccount), Long.valueOf(toAccount), amount,saved.getId());
            return "Transaction committed successfully";

        } catch (Exception ex) {
            auditService.saveAuditFailure(Long.valueOf(fromAccount), Long.valueOf(toAccount), amount, ex.getMessage());
            throw ex; 
        }
	}
	
	@Override
	public List<EntityPayment> fetchAllPayment() {
		return repoEntityPayment.findAll();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** OUTDATED STARTS OD1**/
	
	@Transactional//(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Object begainTransactionV1(BigDecimal amount, Long fromAccount, Long toAccount) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		}
		EntityAccount fromAccountEntity = repoEntityAccount.findByAccountNo(fromAccount.toString())
				.orElseThrow(() -> new RuntimeException("From account not found"));

		EntityAccount toAccountEntity = repoEntityAccount.findByAccountNo(toAccount.toString())
				.orElseThrow(() -> new RuntimeException("To account not found"));

		if (amount.compareTo(fromAccountEntity.getAmount()) > 0) {
			throw new RuntimeException("Insufficient balance");
		}

		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Invalid amount");
		}

		BigDecimal balanceLeft = fromAccountEntity.getAmount().subtract(amount);
		BigDecimal balanceCredit = toAccountEntity.getAmount().add(amount);
		EntityPayment e1 = new EntityPayment();
		e1.setAccountFrom(fromAccountEntity);
		e1.setAccountTo(toAccountEntity);
		e1.setCard(fromAccountEntity.getCardType());
		e1.setTransactionAmount(amount);
		e1.setAccountNoFrom(fromAccount);
		e1.setAccountNoTo(toAccount);
		repoEntityPayment.save(e1);

		setDebitAmount(fromAccountEntity, balanceLeft);
		setCreditAmount(toAccountEntity, balanceCredit);

		return "Transaction Successful commited!";
	}

	private void setCreditAmount(EntityAccount toAccountEntity, BigDecimal balanceCredit) {
		toAccountEntity.setAmount(balanceCredit);
		repoEntityAccount.save(toAccountEntity);
		throw new RuntimeException("fuck you");
	}

	private void setDebitAmount(EntityAccount fromAccountEntity, BigDecimal balanceLeft) {
		fromAccountEntity.setAmount(balanceLeft);
		repoEntityAccount.save(fromAccountEntity);
		
	}
	/** OUTDATED ENDS OD1**/
}
