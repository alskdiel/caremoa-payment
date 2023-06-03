package com.caremoa.payment.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentRequestState {

    REQUESTED("00", "요청"), APPROVED("10", "승인"), REJECTED("20", "거절");
	
	private final String key;
	private final String title;
}
