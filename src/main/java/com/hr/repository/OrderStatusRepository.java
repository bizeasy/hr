package com.hr.repository;

import com.hr.domain.OrderStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OrderStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long>, JpaSpecificationExecutor<OrderStatus> {
}
