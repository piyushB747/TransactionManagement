package net.kanth.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
import net.kanth.enums.LedgerType;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_ledger")
public class EntityLedger {

	    @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ledger_gen")
	    @SequenceGenerator(name="ledger_seq",allocationSize = 100)
	    private Long id;

	    private String accountNo;

	    private BigDecimal amount;   // +ve = credit, -ve = debit

	    @Enumerated(EnumType.STRING)
	    private LedgerType type;     // CREDIT / DEBIT

	    private String referenceId;  // payment id / txn id

	    @CreationTimestamp
	    private LocalDateTime createdAt;
	    
	    private String counterPartyAccount;
	  
	}

