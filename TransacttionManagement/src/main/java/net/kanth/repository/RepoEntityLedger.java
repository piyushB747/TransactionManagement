package net.kanth.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.kanth.entity.EntityLedger;

public interface  RepoEntityLedger extends JpaRepository<EntityLedger, Long>{
	
	 @Query("""
		       SELECT COALESCE(SUM(l.amount), 0)
		       FROM EntityLedger l
		       WHERE l.accountNo = :accountNo
		    """)
		    BigDecimal getBalance(String accountNo);
	 
}
