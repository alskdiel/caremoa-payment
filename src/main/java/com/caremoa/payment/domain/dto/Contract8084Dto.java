package com.caremoa.payment.domain.dto;

import com.caremoa.payment.domain.model.Contract8084;

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
public class Contract8084Dto {

    private Long id;
    private Long memberId;
    private Long helperId;
    private String helperName;
	
	public Contract8084 toEntity() {
		Contract8084 contract = new Contract8084();
		contract.setId(this.id);
		contract.setMemberId(this.memberId);
		contract.setHelperId(this.helperId);
		contract.setHelperName(this.helperName);
		return contract;
	}
}
