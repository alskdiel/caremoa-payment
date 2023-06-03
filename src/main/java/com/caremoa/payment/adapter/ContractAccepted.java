package com.caremoa.payment.adapter;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class ContractAccepted extends AbstractEvent{
	private Long contractId;
	private Long memberId;
	private Long helperId;

	@Builder
	public ContractAccepted(Long contractId, Long memberId, Long helperId) {
		super();
		this.contractId = contractId; 
		this.memberId = memberId;
		this.helperId = helperId;
	}
}
