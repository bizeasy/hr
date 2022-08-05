package com.hr.repository;

import com.hr.domain.OrderTerm;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OrderTerm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderTermRepository extends JpaRepository<OrderTerm, Long> {
}
