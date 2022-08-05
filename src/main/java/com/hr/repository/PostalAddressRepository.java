package com.hr.repository;

import com.hr.domain.PostalAddress;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PostalAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostalAddressRepository extends JpaRepository<PostalAddress, Long>, JpaSpecificationExecutor<PostalAddress> {
}
