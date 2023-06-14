package com.caremoa.payment.adapter;

import java.time.LocalDateTime;

import com.caremoa.payment.domain.dto.Contract8084Dto;
import com.caremoa.payment.domain.model.PaymentMethod;
import com.caremoa.payment.domain.model.PaymentRequestState;
import com.caremoa.payment.domain.model.PaymentType;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class PaymentCompleted extends AbstractEvent{
    private Long id;
    private Contract8084Dto contract;
    private PaymentType paymentType;
    private PaymentMethod paymentMethod;
    private PaymentRequestState paymentRequestState; 
	private LocalDateTime requestDateTime;
	private Integer requestAmount;
	private LocalDateTime responseDateTime;
	private String approveNo;
	
	@Builder
	public PaymentCompleted(Long id, Contract8084Dto contract, PaymentType paymentType, PaymentMethod paymentMethod, PaymentRequestState paymentRequestState,
			LocalDateTime requestDateTime, Integer requestAmount, LocalDateTime responseDateTime, String approveNo) {
		super();
		this.id = id;
		this.contract = contract;
		this.paymentType = paymentType;
		this.paymentMethod = paymentMethod;
		this.paymentRequestState = paymentRequestState;
		this.requestDateTime = requestDateTime;
		this.requestAmount = requestAmount;
		this.responseDateTime = responseDateTime;
		this.approveNo = approveNo;
	}
}
