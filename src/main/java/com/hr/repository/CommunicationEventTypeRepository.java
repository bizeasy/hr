package com.hr.repository;

import com.hr.domain.CommunicationEventType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CommunicationEventType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommunicationEventTypeRepository extends JpaRepository<CommunicationEventType, Long> {
}
