package com.caremoa.payment.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentType {

    NORMAL("00", "정상"), CANCEL("10", "취소");
	
	private final String key;
	private final String title;
}
