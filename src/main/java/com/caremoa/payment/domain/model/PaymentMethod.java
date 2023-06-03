package com.caremoa.payment.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {

    KAKAO("0001", "카카오페이"), NAVER("0002", "네이버페이");
	//KAKAO, NAVER;
	
	private final String key;
	private final String title;
}
