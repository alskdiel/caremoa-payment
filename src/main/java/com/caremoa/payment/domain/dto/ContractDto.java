package com.caremoa.payment.domain.dto;

import com.caremoa.payment.domain.model.Contract;

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
public class ContractDto {

    private Long id;
    private Long memberId;
    private Long helperId;
	
	public Contract toEntity() {
		Contract contract = new Contract();
		contract.setId(this.id);
		contract.setMemberId(this.memberId);
		contract.setHelperId(this.helperId);
		return contract;
	}
}
