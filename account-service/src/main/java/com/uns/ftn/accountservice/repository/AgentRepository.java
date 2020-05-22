package com.uns.ftn.accountservice.repository;

import com.uns.ftn.accountservice.domain.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {
}
