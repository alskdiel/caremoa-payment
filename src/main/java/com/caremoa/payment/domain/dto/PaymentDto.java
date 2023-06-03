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
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ContractDto getContract() {
		return contract;
	}
	public void setContract(ContractDto contract) {
		this.contract = contract;
	}
	public PaymentType getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public PaymentRequestState getPaymentRequestState() {
		return paymentRequestState;
	}
	public void setPaymentRequestState(PaymentRequestState paymentRequestState) {
		this.paymentRequestState = paymentRequestState;
	}
	public LocalDateTime getRequestDateTime() {
		return requestDateTime;
	}
	public void setRequestDateTime(LocalDateTime requestDateTime) {
		this.requestDateTime = requestDateTime;
	}
	public Integer getRequestAmount() {
		return requestAmount;
	}
	public void setRequestAmount(Integer requestAmount) {
		this.requestAmount = requestAmount;
	}
	public LocalDateTime getResponseDateTime() {
		return responseDateTime;
	}
	public void setResponseDateTime(LocalDateTime responseDateTime) {
		this.responseDateTime = responseDateTime;
	}
	public String getApproveNo() {
		return approveNo;
	}
	public void setApproveNo(String approveNo) {
		this.approveNo = approveNo;
	}
	
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
