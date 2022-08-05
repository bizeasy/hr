package com.hr.repository;

import com.hr.domain.OrderContactMech;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OrderContactMech entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderContactMechRepository extends JpaRepository<OrderContactMech, Long>, JpaSpecificationExecutor<OrderContactMech> {
}
