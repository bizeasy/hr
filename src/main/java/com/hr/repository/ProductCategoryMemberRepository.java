package com.hr.repository;

import com.hr.domain.ProductCategoryMember;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductCategoryMember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductCategoryMemberRepository extends JpaRepository<ProductCategoryMember, Long>, JpaSpecificationExecutor<ProductCategoryMember> {
}
