package com.caremoa.payment.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.caremoa.payment.domain.dto.ContractDto;

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
public class Contract {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long memberId;
	private Long helperId;


	@OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();
/*
	public Contract(Long id, Long memberId, Long helperId, List<Payment> payments) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.helperId = helperId;
		this.payments = payments;
	}*/

	public ContractDto toDto() {
		ContractDto contractDto = new ContractDto();
		contractDto.setId(this.id);
		contractDto.setMemberId(this.memberId);
		contractDto.setHelperId(this.helperId);
		return contractDto;
	}
}
