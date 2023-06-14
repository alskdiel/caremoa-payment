package com.caremoa.payment.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caremoa.payment.domain.dto.Contract8084Dto;
import com.caremoa.payment.domain.model.Contract8084;
import com.caremoa.payment.domain.repository.ContractRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {
	private final ContractRepository contractRepository;
	
	@Transactional
	public Contract8084Dto createContract(Contract8084Dto contract8084Dto) {
        Contract8084 savedContract = contractRepository.save(contract8084Dto.toEntity());
        return savedContract.toDto();
	}
	
}
