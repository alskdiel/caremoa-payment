package com.caremoa.payment.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.caremoa.payment.domain.dto.PaymentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Data
@AllArgsConstructor
@Builder
public class Payment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;
	//private Long contractId;
	
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;
	
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	
	@Enumerated(EnumType.STRING)
	private PaymentRequestState paymentRequestState;
	
	private LocalDateTime requestDateTime;
	private Integer requestAmount;
	private LocalDateTime responseDateTime;
	private String approveNo;

	@OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentReject> paymentRejects = new ArrayList<>();

	public PaymentDto toDto() {
		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setId(this.id);
		paymentDto.setContract(this.contract.toDto());
		paymentDto.setPaymentType(this.paymentType);
		paymentDto.setPaymentMethod(this.paymentMethod);
		paymentDto.setPaymentRequestState(this.paymentRequestState);
		paymentDto.setRequestDateTime(this.requestDateTime);
		paymentDto.setRequestAmount(this.requestAmount);
		paymentDto.setResponseDateTime(this.responseDateTime);
		paymentDto.setApproveNo(this.approveNo);
		return paymentDto;
	}
}
