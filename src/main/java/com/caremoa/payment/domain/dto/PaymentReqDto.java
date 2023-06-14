package com.caremoa.payment.domain.dto;

import java.time.LocalDateTime;

import com.caremoa.payment.domain.model.PaymentMethod;
import com.caremoa.payment.domain.model.PaymentRequestState;
import com.caremoa.payment.domain.model.PaymentType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class PaymentReqDto {

	protected Long id;
	private Long contractId;
	private Long memberId;
	private Long helperId;
	private String helperName;
	protected PaymentType paymentType;
	protected PaymentMethod paymentMethod;
	protected PaymentRequestState paymentRequestState;
	protected LocalDateTime requestDateTime;
	protected Integer requestAmount;
	protected LocalDateTime responseDateTime;
	protected String approveNo;

	public Contract8084Dto getContract() {
		return new Contract8084Dto(this.contractId, this.memberId, this.helperId, this.helperName);
	}

	public Payment8084Dto getPayment() {
		if(this.paymentMethod == null) {
			this.paymentMethod = PaymentMethod.KAKAO;
		}
		return Payment8084Dto.builder().contract(this.getContract())
				                       .paymentMethod(this.paymentMethod)
				                       .requestAmount(this.requestAmount).build();
	}

	public void setPayment(Payment8084Dto paymentDto) {
		this.id = paymentDto.getId();
    	this.paymentType = paymentDto.getPaymentType();
    	this.paymentMethod = paymentDto.getPaymentMethod();
    	this.paymentRequestState = paymentDto.getPaymentRequestState();
    	this.requestDateTime = paymentDto.getRequestDateTime();
    	this.requestAmount = paymentDto.getRequestAmount();
    	this.responseDateTime = paymentDto.getResponseDateTime();
    	this.approveNo = paymentDto.getApproveNo();	
	}
}
