
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

@FeignClient(name = "contract", url = "http://contract:8095/") // Feign Client 설정
public interface ContractFeignClient {

    @PostMapping("/create/")
    PaymentDto postContractData(@RequestBody PaymentDto paymentDto);
}
