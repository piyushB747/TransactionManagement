package net.kanth.service;

import java.math.BigDecimal;

public interface ServiceEntityAuditLogs {
	void saveAuditSuccess(Long fromAcc, Long toAcc, BigDecimal amount,Long paymentId);

	void saveAuditFailure(Long fromAcc, Long toAcc, BigDecimal amount, String reason);

}
