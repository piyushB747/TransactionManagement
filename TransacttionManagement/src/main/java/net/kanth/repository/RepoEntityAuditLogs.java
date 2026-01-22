package net.kanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kanth.entity.EntityAuditLogs;

public interface RepoEntityAuditLogs extends JpaRepository<EntityAuditLogs, Long>{

}
