package com.hr.repository;

import com.hr.domain.PaymentApplication;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaymentApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentApplicationRepository extends JpaRepository<PaymentApplication, Long>, JpaSpecificationExecutor<PaymentApplication> {
}
