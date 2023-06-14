package com.caremoa.payment.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.caremoa.payment.adapter.ExternalFeignClient;
import com.caremoa.payment.adapter.PaymentCompleted;
import com.caremoa.payment.domain.dto.Contract8084Dto;
import com.caremoa.payment.domain.dto.Payment8084Dto;
import com.caremoa.payment.domain.dto.PaymentReqDto;
import com.caremoa.payment.domain.model.PaymentRequestState;
import com.caremoa.payment.domain.model.PaymentType;
import com.caremoa.payment.domain.service.ContractService;
import com.caremoa.payment.domain.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final StreamBridge streamBridge;
    private final PaymentService paymentService;
    private final ContractService contractService;
    private final ExternalFeignClient externalFeignClient;
    //private final ContractFeignClient contractFeignClient;

    @GetMapping("/payments")
    public List<Payment8084Dto> getAllPayments() {
    	log.debug("getAllPayments()");
        return paymentService.getAllPayments();
    }

    @GetMapping("/payments/{id}")
    public Payment8084Dto getPaymentById(@PathVariable("id") Long id) {
    	log.debug("getPaymentById()");
        return paymentService.getPaymentById(id);
    }

    @PostMapping("/payments")
    //public Payment8084Dto createPayment(@RequestBody Payment8084Dto payment8084Dto) {
    public PaymentReqDto createPayment(@RequestBody PaymentReqDto paymentReqDto) {
    	log.debug("createPayment()");
    	Contract8084Dto contractDto = paymentReqDto.getContract();
		contractService.createContract(contractDto);
    	
    	Payment8084Dto paymentDto = paymentReqDto.getPayment();
    	paymentDto.setContract(contractDto);
    	paymentDto.setPaymentRequestState(PaymentRequestState.REQUESTED);
    	paymentDto.setPaymentType(PaymentType.NORMAL);
    	paymentDto.setRequestDateTime(LocalDateTime.now());
    	
    	/* EXTERNAL 승인 요청 */
    	Payment8084Dto requestPaymentDto = new Payment8084Dto();
    	requestPaymentDto.setRequestAmount(paymentDto.getRequestAmount());
    	requestPaymentDto.setPaymentRequestState(paymentDto.getPaymentRequestState());
    	Payment8084Dto responsePaymentDto = externalFeignClient.postExternalData(requestPaymentDto);
    	
    	paymentDto.setPaymentRequestState(responsePaymentDto.getPaymentRequestState());
    	paymentDto.setResponseDateTime(LocalDateTime.now());
    	paymentDto.setApproveNo(responsePaymentDto.getApproveNo());
    	paymentService.createPayment(paymentDto);
    	
    	this.publishPaymentCompleted(paymentDto);
    	
    	paymentReqDto.setPayment(paymentDto);
        return paymentReqDto;
    }

    @PutMapping("/payments/{id}")
    public Payment8084Dto updatePayment(@PathVariable("id") Long id, @RequestBody Payment8084Dto paymentDto) {
    	log.debug("updatePayment()");
        return paymentService.updatePayment(id, paymentDto);
    }

    @DeleteMapping("/payments/{id}")
    public void deletePayment(@PathVariable("id") Long id) {
    	log.debug("deletePayment()");
    	Payment8084Dto paymentDto = paymentService.getPaymentById(id);
    	paymentDto.setId(null);
    	paymentDto.setPaymentType(PaymentType.CANCEL);
    	paymentDto.setRequestDateTime(LocalDateTime.now());
    	
    	/* EXTERNA 취소 요청 */
    	Payment8084Dto requestPaymentDto = new Payment8084Dto();
    	requestPaymentDto.setRequestAmount(paymentDto.getRequestAmount());
    	requestPaymentDto.setApproveNo(paymentDto.getApproveNo());
    	requestPaymentDto.setPaymentRequestState(PaymentRequestState.REQUESTED);
    	Payment8084Dto responsePaymentDto = externalFeignClient.deleteExternalData(requestPaymentDto);
    	paymentDto.setPaymentRequestState(responsePaymentDto.getPaymentRequestState());
    	paymentDto.setResponseDateTime(LocalDateTime.now());
        paymentService.createPayment(paymentDto);
        
        this.publishPaymentCompleted(paymentDto);
    	
    }
    
    @GetMapping("/members/{id}/payments")
    public List<Payment8084Dto> getMemberPayments(@PathVariable("id") Long id) {
    	log.debug("getUserPayments()");
        return paymentService.getPaymentsByMemberId(id);
    }
	
	private void publishPaymentCompleted(Payment8084Dto paymentDto) {
		try {
			PaymentCompleted paymentCompleted = PaymentCompleted.builder().id(paymentDto.getId())
					.contract(paymentDto.getContract()).paymentType(paymentDto.getPaymentType())
					.paymentMethod(paymentDto.getPaymentMethod()).paymentRequestState(paymentDto.getPaymentRequestState())
					.requestDateTime(paymentDto.getRequestDateTime()).requestAmount(paymentDto.getRequestAmount())
					.responseDateTime(paymentDto.getRequestDateTime()).approveNo(paymentDto.getApproveNo()).build();
			
			String json = paymentCompleted.toJson();
			log.info("before publish");
		    if( json != null ){
		          streamBridge.send("basicProducer-out-0", MessageBuilder.withPayload(json)
		      	 		.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
		     }
		    log.info("after publish");
		} catch (Exception e) {
			log.info("publish {}", e.getMessage());
		}
	}
	
}
