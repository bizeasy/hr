package com.hr.repository;

import com.hr.domain.OrderTermType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OrderTermType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderTermTypeRepository extends JpaRepository<OrderTermType, Long> {
}
