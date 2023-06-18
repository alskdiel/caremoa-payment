
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

import com.caremoa.payment.domain.dto.Payment8084Dto;

@FeignClient(name = "external", url = "http://13.125.214.14:8099/") // Feign Client 설정
public interface ExternalFeignClient {

    @PostMapping("/external/approve-payment")
    Payment8084Dto postExternalData(@RequestBody Payment8084Dto paymentDto);

    @PostMapping("/external/cancel-payment")
    Payment8084Dto deleteExternalData(@RequestBody Payment8084Dto paymentDto);
}
