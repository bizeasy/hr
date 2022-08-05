package com.hr.repository;

import com.hr.domain.OrderItemType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OrderItemType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderItemTypeRepository extends JpaRepository<OrderItemType, Long> {
}
