package com.caremoa.payment.domain.dto;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.caremoa.payment.domain.model.Contract8084;
import com.caremoa.payment.domain.model.Payment8084;
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
public class Payment8084Dto {

    private Long id;
    private Contract8084Dto contract;
    private PaymentType paymentType;
    private PaymentMethod paymentMethod;
    private PaymentRequestState paymentRequestState; 
    private LocalDateTime requestDateTime;
    private Integer requestAmount;
    private LocalDateTime responseDateTime;
    private String approveNo;
	
	public Payment8084 toEntity() {
		Payment8084 payment = new Payment8084();
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
