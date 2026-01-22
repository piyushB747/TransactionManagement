package net.kanth.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kanth.enums.TransactionStatus;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "audit_tbl")
public class EntityAuditLogs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="sequence_audit")
	@SequenceGenerator(name="audit_seq",allocationSize = 50)
	private Long id;
	
	private BigDecimal amount;
	
	@Enumerated(EnumType.STRING) // This tells JPA to save the name "OPEN" instead of 0
	private TransactionStatus status;
	
	private Long paymentId;
	
	private Long fromAccount;
	
	private Long toAccount;
	
	
	@Column(length = 1000) // Increase limit to 1000 characters
	private String message;
	
	@CreationTimestamp
	private LocalDateTime localTimeDate;
	
	
}
