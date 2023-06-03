package com.caremoa.payment.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.caremoa.payment.adapter.ContractAccepted;
import com.caremoa.payment.adapter.PaymentFeignClient;
import com.caremoa.payment.domain.dto.PaymentDto;
import com.caremoa.payment.domain.model.PaymentRequestState;
import com.caremoa.payment.domain.model.PaymentType;
import com.caremoa.payment.domain.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final StreamBridge streamBridge;
    private final PaymentService paymentService;
    private final PaymentFeignClient paymentFeignClient;


    @GetMapping("/payments")
    public List<PaymentDto> getAllPayments() {
    	log.debug("getAllPayments()");
        return paymentService.getAllPayments();
    }

    @GetMapping("/payments/{id}")
    public PaymentDto getPaymentById(@PathVariable("id") Long id) {
    	log.debug("getPaymentById()");
        return paymentService.getPaymentById(id);
    }

    @PostMapping("/payments")
    public PaymentDto createPayment(@RequestBody PaymentDto paymentDto) {
    	paymentDto.setId(null);
    	log.debug("createPayment()");
    	
    	/* EXTERNAL 승인 요청 */
    	PaymentDto requestPaymentDto = new PaymentDto();
    	requestPaymentDto.setRequestAmount(paymentDto.getRequestAmount());
    	requestPaymentDto.setPaymentRequestState(paymentDto.getPaymentRequestState());
    	paymentDto.setRequestDateTime(LocalDateTime.now());
    	PaymentDto responsePaymentDto = paymentFeignClient.postData(requestPaymentDto);
    	paymentDto.setPaymentType(PaymentType.NORMAL);
    	paymentDto.setPaymentRequestState(responsePaymentDto.getPaymentRequestState());
    	paymentDto.setResponseDateTime(LocalDateTime.now());
    	paymentDto.setApproveNo(responsePaymentDto.getApproveNo());
        return paymentService.createPayment(paymentDto);
    }

    @PutMapping("/payments/{id}")
    public PaymentDto updatePayment(@PathVariable("id") Long id, @RequestBody PaymentDto paymentDto) {
    	log.debug("updatePayment()");
        return paymentService.updatePayment(id, paymentDto);
    }

    @DeleteMapping("/payments/{id}")
    public void deletePayment(@PathVariable("id") Long id) {
    	log.debug("deletePayment()");
    	
    	PaymentDto paymentDto = paymentService.getPaymentById(id);
    	paymentDto.setId(null);
    	
    	/* EXTERNA 취소 요청 */
    	PaymentDto requestPaymentDto = new PaymentDto();
    	requestPaymentDto.setRequestAmount(paymentDto.getRequestAmount());
    	requestPaymentDto.setApproveNo(paymentDto.getApproveNo());
    	requestPaymentDto.setPaymentRequestState(PaymentRequestState.REQUESTED);
    	paymentDto.setRequestDateTime(LocalDateTime.now());
    	paymentDto.setPaymentType(PaymentType.CANCEL);
    	PaymentDto responsePaymentDto = paymentFeignClient.deleteData(requestPaymentDto);
    	paymentDto.setPaymentRequestState(responsePaymentDto.getPaymentRequestState());
    	paymentDto.setResponseDateTime(LocalDateTime.now());
        paymentService.createPayment(paymentDto);
        
    	/*
        paymentService.deletePayment(id);
        */
    }
    
    @GetMapping("/members/{id}/payments")
    public List<PaymentDto> getMemberPayments(@PathVariable("id") Long id) {
    	log.debug("getUserPayments()");
        return paymentService.getMemberPayments(id);
    }

	@Operation(summary = "카프카 Publish 테스트" , description = "카프카 Publish 테스트" )
	@GetMapping("/testkafka/{contractId}/{memberId}/{helperId}")
	public ResponseEntity<HttpStatus> test(@PathVariable("memberId") Long contractId, @PathVariable("memberId") Long memberId, @PathVariable("helperId") Long helperId) {
		try {
			ContractAccepted xx = ContractAccepted.builder().contractId(contractId).helperId(helperId).memberId(memberId).build();
			String json = xx.toJson();
			log.info("before publish");
		    if( json != null ){
		          streamBridge.send("basicProducer-out-0", MessageBuilder.withPayload(json)
		      	 		.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
		     }
		    log.info("after publish");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.info("publish {}", e.getMessage());
			return ResponseEntity.internalServerError().body(null);
		}
	}
}