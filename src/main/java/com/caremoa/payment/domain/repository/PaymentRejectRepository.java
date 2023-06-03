package com.caremoa.payment.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caremoa.payment.domain.model.PaymentReject;

@Repository
public interface PaymentRejectRepository extends JpaRepository<PaymentReject, Long> {
}