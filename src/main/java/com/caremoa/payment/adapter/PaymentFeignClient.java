
package com.caremoa.payment.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.caremoa.payment.domain.dto.PaymentDto;

@FeignClient(name = "payment", url = "http://localhost:8099/") // Feign Client 설정
public interface PaymentFeignClient {
	/*
	@GetMapping("/payments")
    Page<PaymentDto> getAll(Pageable pageable);
    
    @GetMapping("/payments/{id}")
    PaymentDto getById(@PathVariable("id") Long id);

    @PostMapping("/payments")
    PaymentDto postData(@RequestBody PaymentDto paymentDto);

    @PutMapping("/payments/{id}")
    PaymentDto putData(@RequestBody PaymentDto paymentDto, @PathVariable("id") Long id);

	@PatchMapping("/payments/{id}")
	PaymentDto patchData(@RequestBody PaymentDto paymentDto, @PathVariable("id") Long id);

    @DeleteMapping("/payments/{id}")
    void deleteData(@PathVariable("id") Long id);
    */
    @PostMapping("/external/approve-payment")
    PaymentDto postData(@RequestBody PaymentDto paymentDto);

    @PostMapping("/external/cancel-payment")
    PaymentDto deleteData(@RequestBody PaymentDto paymentDto);
}
