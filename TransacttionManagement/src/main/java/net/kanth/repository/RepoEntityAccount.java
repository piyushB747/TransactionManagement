package net.kanth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;
import net.kanth.entity.EntityAccount;

public interface RepoEntityAccount extends JpaRepository<EntityAccount, Long> {

	@Query(name = "findAccountByAccountNo")
	int findAccountByAccountNo(long newAccountNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<EntityAccount> findByAccountNo(String accountNo);
}
