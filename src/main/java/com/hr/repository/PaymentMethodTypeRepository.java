package com.hr.repository;

import com.hr.domain.PaymentMethodType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaymentMethodType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentMethodTypeRepository extends JpaRepository<PaymentMethodType, Long> {
}
