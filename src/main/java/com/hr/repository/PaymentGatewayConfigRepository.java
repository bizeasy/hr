package com.hr.repository;

import com.hr.domain.PaymentGatewayConfig;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaymentGatewayConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentGatewayConfigRepository extends JpaRepository<PaymentGatewayConfig, Long> {
}
