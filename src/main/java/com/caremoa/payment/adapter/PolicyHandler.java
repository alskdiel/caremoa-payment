package com.caremoa.payment.adapter;

import java.util.function.Consumer;

import org.springdoc.api.ErrorMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.caremoa.payment.domain.model.Contract;
import com.caremoa.payment.domain.service.ContractService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PolicyHandler {

	private long errorOccur = 0;
	private final ContractService contractService;

    @Bean
    Consumer<ContractAccepted> basicConsumer() {
		return contractCompleted -> {
			log.info( "{} : {}", contractCompleted.getEventType(), contractCompleted.validate());
			log.info("teamUpdated 이벤트 수신 : {}", contractCompleted);
		};
	}


    @Bean
    Consumer<ErrorMessage> KafkaErrorHandler() {
		return e -> {
	    	errorOccur++;
	        log.error("에러 발생: {}, 횟수: {}", e, errorOccur);
	    };
	}
    
    /**
     * @methodName    : ReflectionScore
     * @date          : 2023.05.19
     * @description   : 점수반영
     * @param paymentId
    */
    private void createContract(Long contractId, Long memberId, Long helperId) {
    	try {
    		log.debug("ReflectionScore {}, {}, {}", contractId, memberId, helperId);
    		Contract contract = new Contract(contractId, memberId, helperId);
    		contractService.createContract(contract);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * @methodName    : wheneverContracAcceptedThenCreateContract
     * @date          : 2023.05.19
     * @description   : 계약이 완료될 때 계약정보 저장
     * @return
    */
    @Bean
    Consumer<ContractAccepted>wheneverContractAcceptedThenCreateContract() {
        	return contractAccepted -> {
    		log.debug("Call contractAccepted : {}", contractAccepted.validate() );
    		if ( contractAccepted.validate() ) {
    			createContract(contractAccepted.getContractId(), contractAccepted.getMemberId(), contractAccepted.getHelperId());
    		}
    	};
    }
    
    
}
