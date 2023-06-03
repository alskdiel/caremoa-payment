package com.caremoa.payment.adapter;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* @packageName    : com.caremoa.member.adapter
* @fileName       : ContractCompleted.java
* @author         : 이병관
* @date           : 2023.05.19
* @description    : 계약수락 Polish Event
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.05.19        이병관       최초 생성
*/
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
