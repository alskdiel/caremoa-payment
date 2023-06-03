package com.caremoa.payment.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caremoa.payment.domain.model.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
	List<Contract> findByMemberId(Long memberId);
}