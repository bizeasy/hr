package com.hr.repository;

import com.hr.domain.PaymentGatewayResponse;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaymentGatewayResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentGatewayResponseRepository extends JpaRepository<PaymentGatewayResponse, Long> {
}
