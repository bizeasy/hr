package com.hr.repository;

import com.hr.domain.CommunicationEvent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CommunicationEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommunicationEventRepository extends JpaRepository<CommunicationEvent, Long> {
}
