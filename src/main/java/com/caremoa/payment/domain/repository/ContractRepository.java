package com.caremoa.payment.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caremoa.payment.domain.model.Contract8084;

@Repository
public interface ContractRepository extends JpaRepository<Contract8084, Long> {
	List<Contract8084> findByMemberId(Long memberId);
}