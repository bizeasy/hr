package com.hr.repository;

import com.hr.domain.SalesChannel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SalesChannel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesChannelRepository extends JpaRepository<SalesChannel, Long> {
}
