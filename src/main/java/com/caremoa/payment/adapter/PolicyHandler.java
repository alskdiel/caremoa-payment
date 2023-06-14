package com.caremoa.payment.adapter;

import java.util.Map;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.caremoa.payment.domain.dto.ContractDto;
import com.caremoa.payment.domain.service.ContractService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PolicyHandler {

	private final ContractService contractService;

    @Bean
    Consumer<Map<String, Object>> basicConsumer() {
		return mapData -> {
			ObjectMapper mapper = new ObjectMapper();
			log.debug(mapData.toString());
			
			switch (mapData.get("eventType").toString()) {
				case "ContractAccepted":
					ContractAccepted contractAccepted = mapper.convertValue(mapData, ContractAccepted.class);
					log.debug("contractCompleted : {}", contractAccepted.toString());
	    			createContract(contractAccepted.getContractId(), contractAccepted.getMemberId(), contractAccepted.getHelperId());
					break;
				default: // 처리가 정의되지 않은 이벤트
					log.debug("Undefined EventType : {}", mapData.get("eventType").toString());
					break;
			}
		};
	}

    
    /**
     * @methodName    : createContract
     * @date          : 2023.05.19
     * @description   : 계약 생성
     * @param         : contractId, memberId, helperId
    */
    private void createContract(Long contractId, Long memberId, Long helperId) {
    	try {
    		log.debug("ReflectionScore {}, {}, {}", contractId, memberId, helperId);
    		ContractDto contractDto = new ContractDto(contractId, memberId, helperId);
    		contractService.createContract(contractDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
}
