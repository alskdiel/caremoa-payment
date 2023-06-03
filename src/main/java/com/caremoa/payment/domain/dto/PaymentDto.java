package com.caremoa.payment.domain.dto;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.caremoa.payment.domain.model.Contract;
import com.caremoa.payment.domain.model.Payment;
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
public class PaymentDto {

    private Long id;
    private ContractDto contract;
    private PaymentType paymentType;
    private PaymentMethod paymentMethod;
    private PaymentRequestState paymentRequestState; 
	private LocalDateTime requestDateTime;
	private Integer requestAmount;
	private LocalDateTime responseDateTime;
	private String approveNo;
	
	public Payment toEntity() {
		Payment payment = new Payment();
		payment.setId(this.id);
		payment.setContract(this.contract.toEntity());
		payment.setPaymentType(this.paymentType);
		payment.setPaymentMethod(this.paymentMethod);
		payment.setPaymentRequestState(this.paymentRequestState);
		payment.setRequestDateTime(this.requestDateTime);
		payment.setRequestAmount(this.requestAmount);
		payment.setResponseDateTime(this.responseDateTime);
		payment.setApproveNo(this.approveNo);
		return payment;
	}
	
}
