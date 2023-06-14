package com.caremoa.payment.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.caremoa.payment.domain.dto.PaymentDto;
import com.caremoa.payment.domain.model.Contract;
import com.caremoa.payment.domain.model.Payment;
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

    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(Payment::toDto).collect(Collectors.toList());
    }

    public PaymentDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        return payment.toDto();
    }

    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment savedPayment = paymentRepository.save(paymentDto.toEntity());
        return savedPayment.toDto();
    }

    public PaymentDto updatePayment(Long id, PaymentDto paymentDto) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        existingPayment.setContract(paymentDto.getContract().toEntity());
        existingPayment.setPaymentType(paymentDto.getPaymentType());
        existingPayment.setPaymentMethod(paymentDto.getPaymentMethod());
        existingPayment.setPaymentRequestState(paymentDto.getPaymentRequestState());
        existingPayment.setRequestDateTime(paymentDto.getRequestDateTime());
        existingPayment.setRequestAmount(paymentDto.getRequestAmount());
        existingPayment.setResponseDateTime(paymentDto.getResponseDateTime());
        existingPayment.setApproveNo(paymentDto.getApproveNo());
        Payment updatedPayment = paymentRepository.save(existingPayment);
        return updatedPayment.toDto();
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public List<PaymentDto> getPaymentsByMemberId(Long id) {
        List<Payment> payments = new ArrayList<Payment>();
        List<Contract> contract = contractRepository.findByMemberId(id);
        for(Contract c : contract) {
        	List<Payment> cPayments = c.getPayments();
        	for(Payment p : cPayments) {
        		payments.add(p);
        	}
        }
        return payments.stream().map(Payment::toDto).collect(Collectors.toList());
                
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
