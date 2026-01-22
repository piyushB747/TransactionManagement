package net.kanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kanth.entity.EntityPayment;

public interface RepoEntityPayment extends JpaRepository<EntityPayment, Long>{

}
