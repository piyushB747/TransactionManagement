package net.kanth.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kanth.entity.EntityAuditLogs;
import net.kanth.enums.TransactionStatus;
import net.kanth.repository.RepoEntityAuditLogs;
import net.kanth.service.ServiceEntityAuditLogs;

@Slf4j
@AllArgsConstructor
@Service
public class ServiceEntityAuditLogsImpl implements ServiceEntityAuditLogs{

	private RepoEntityAuditLogs repoEntityAuditLogs;
	
   @Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void saveAuditSuccess(Long fromAcc, Long toAcc, BigDecimal amount,Long paymentId) {
    	
    	log.info("Transaction Audit is Saving");
    	EntityAuditLogs audit = new EntityAuditLogs();
        audit.setFromAccount(fromAcc);
        audit.setToAccount(toAcc);
        audit.setAmount(amount);
        audit.setStatus(TransactionStatus.SUCCESS);
        audit.setMessage("Success");
        audit.setPaymentId(paymentId);
        repoEntityAuditLogs.save(audit);
	} 
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void saveAuditFailure(Long fromAcc, Long toAcc, BigDecimal amount, String reason) {
    	EntityAuditLogs audit = new EntityAuditLogs();
        audit.setFromAccount(fromAcc);
        audit.setToAccount(toAcc);
        audit.setAmount(amount);
        audit.setStatus(TransactionStatus.FAILED);
        audit.setMessage(reason);
        
        repoEntityAuditLogs.save(audit);
	}
}
