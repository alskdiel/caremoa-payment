package com.caremoa.payment.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.caremoa.payment.domain.dto.Payment8084Dto;
import com.caremoa.payment.domain.model.Contract8084;
import com.caremoa.payment.domain.model.Payment8084;
import com.caremoa.payment.domain.repository.ContractRepository;
import com.caremoa.payment.domain.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
	
	private final PaymentRepository paymentRepository;
	private final ContractRepository contractRepository;

    public List<Payment8084Dto> getAllPayments() {
        List<Payment8084> payments = paymentRepository.findAll();
        return payments.stream().map(Payment8084::toDto).collect(Collectors.toList());
    }

    public Payment8084Dto getPaymentById(Long id) {
        Payment8084 payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        return payment.toDto();
    }

    public Payment8084Dto createPayment(Payment8084Dto paymentDto) {
        Payment8084 savedPayment = paymentRepository.save(paymentDto.toEntity());
        return savedPayment.toDto();
    }

    public Payment8084Dto updatePayment(Long id, Payment8084Dto paymentDto) {
        Payment8084 existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        existingPayment.setContract(paymentDto.getContract().toEntity());
        existingPayment.setPaymentType(paymentDto.getPaymentType());
        existingPayment.setPaymentMethod(paymentDto.getPaymentMethod());
        existingPayment.setPaymentRequestState(paymentDto.getPaymentRequestState());
        existingPayment.setRequestDateTime(paymentDto.getRequestDateTime());
        existingPayment.setRequestAmount(paymentDto.getRequestAmount());
        existingPayment.setResponseDateTime(paymentDto.getResponseDateTime());
        existingPayment.setApproveNo(paymentDto.getApproveNo());
        Payment8084 updatedPayment = paymentRepository.save(existingPayment);
        return updatedPayment.toDto();
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public List<Payment8084Dto> getPaymentsByMemberId(Long id) {
        List<Payment8084> payments = new ArrayList<Payment8084>();
        List<Contract8084> contract = contractRepository.findByMemberId(id);
        for(Contract8084 c : contract) {
        	List<Payment8084> cPayments = c.getPayments();
        	for(Payment8084 p : cPayments) {
        		payments.add(p);
        	}
        }
        return payments.stream().map(Payment8084::toDto).collect(Collectors.toList());
                
    }
/*
	@Transactional(readOnly=true)
	public Page<PaymentDto> getAllPayments(Pageable pageable) throws Exception, ApiException {
		log.info("getAll");
		return paymentRepository.findAll(pageable);
	}

	@Transactional(readOnly=true)
	public Payment getById(Long id) throws Exception, ApiException {
		Optional<Payment> data = paymentRepository.findById(id);

		if (data.isPresent()) {
			return data.get();
		} else {
			throw new ApiException(HttpStatus.NOT_FOUND, String.format("Payment id=[%d] Not Found", id));
		}
	}

	@Transactional
	public Payment postData(Payment newData) throws Exception, ApiException {
		newData = paymentRepository.save(newData);
		return newData;
	}

	@Transactional
	public Payment putData(Payment newData, Long id) throws Exception, ApiException {
		return paymentRepository.findById(id) //
				.map(oldData -> {
					newData.setId(oldData.getId());
					

					newData.setContractId(oldData.getContractId());
					newData.setPaymentType(oldData.getPaymentType());
					newData.setPaymentMethod(oldData.getPaymentMethod());
					newData.setPaymentRequestState(oldData.getPaymentRequestState());
					newData.setRequestDateTime(oldData.getRequestDateTime());
					newData.setRequestAmount(oldData.getRequestAmount());
					newData.setResponseDateTime(oldData.getResponseDateTime());
					newData.setApproveNo(oldData.getApproveNo());
					return paymentRepository.save(newData);
				}).orElseGet(() -> {
					throw new ApiException(HttpStatus.NOT_FOUND, String.format("Payment id=[%d] Not Found", id));
				});
	}


	@Transactional
	public Payment patchData(Payment newData, Long id) throws Exception, ApiException {
		return paymentRepository.findById(id) //
				.map(oldData -> {
					if(newData.getResponseDateTime() != null ) oldData.setResponseDateTime(newData.getResponseDateTime());
					if(newData.getApproveNo() != null ) oldData.setApproveNo(newData.getApproveNo());
					return paymentRepository.save(oldData);
				}).orElseGet(() -> {
					throw new ApiException(HttpStatus.NOT_FOUND, String.format("Payment id=[%d] Not Found", id));
				});
	}


	public void deleteData(@PathVariable("id") Long id) throws Exception, ApiException {
		paymentRepository.deleteById(id);
	}
	*/
}
