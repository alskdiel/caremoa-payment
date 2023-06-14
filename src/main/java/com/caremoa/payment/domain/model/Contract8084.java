package com.caremoa.payment.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.caremoa.payment.domain.dto.Contract8084Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Data
@AllArgsConstructor
@Builder
public class Contract8084 {
	@Id
	private Long id;
	
	private Long memberId;
	private Long helperId;
	private String helperName;
	
	@OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment8084> payments = new ArrayList<>();

	public Contract8084(Long id, Long memberId, Long helperId, String helperName) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.helperId = helperId;
		this.helperName = helperName;
	}

	public Contract8084Dto toDto() {
		Contract8084Dto contractDto = new Contract8084Dto();
		contractDto.setId(this.id);
		contractDto.setMemberId(this.memberId);
		contractDto.setHelperId(this.helperId);
		contractDto.setHelperName(this.helperName);
		return contractDto;
	}
}
