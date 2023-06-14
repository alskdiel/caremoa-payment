package com.caremoa.payment.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caremoa.payment.domain.dto.ContractDto;
import com.caremoa.payment.domain.model.Contract;
import com.caremoa.payment.domain.repository.ContractRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {
	private final ContractRepository contractRepository;
	
	@Transactional
	public ContractDto createContract(ContractDto contractDto) throws Exception {

        Contract savedContract = contractRepository.save(contractDto.toEntity());
        return savedContract.toDto();
	}
}
