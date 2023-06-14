package com.caremoa.payment.domain.dto;

import java.time.LocalDateTime;

import com.caremoa.payment.domain.model.PaymentMethod;
import com.caremoa.payment.domain.model.PaymentRequestState;
import com.caremoa.payment.domain.model.PaymentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class PaymentReqDto {

	private Long id;
	private Long contractId;
	private Long memberId;
	private Long helperId;
	private String helperName;
	private PaymentType paymentType;
	private PaymentMethod paymentMethod;
	private PaymentRequestState paymentRequestState;
	private LocalDateTime requestDateTime;
	private Integer requestAmount;
	private LocalDateTime responseDateTime;
	private String approveNo;

	public PaymentReqDto(Contract8084Dto contractDto, Payment8084Dto paymentDto) {
		this.isetContract(contractDto);
		this.isetPayment(paymentDto);
	}
	
	public Contract8084Dto igetContract() {
		return new Contract8084Dto(this.contractId, this.memberId, this.helperId, this.helperName);
	}

	public Payment8084Dto igetPayment() {
		if(this.paymentMethod == null) {
			this.paymentMethod = PaymentMethod.KAKAO;
		}
		return Payment8084Dto.builder().contract(this.igetContract())
				                       .paymentMethod(this.paymentMethod)
				                       .requestAmount(this.requestAmount).build();
	}
	
	public void isetContract(Contract8084Dto contractDto) {
		this.contractId = contractDto.getId();
		this.memberId = contractDto.getMemberId();
		this.helperId = contractDto.getHelperId();
		this.helperName = contractDto.getHelperName();
	}

	public void isetPayment(Payment8084Dto paymentDto) {
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
