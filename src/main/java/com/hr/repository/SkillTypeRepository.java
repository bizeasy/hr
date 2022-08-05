package com.hr.repository;

import com.hr.domain.SkillType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SkillType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillTypeRepository extends JpaRepository<SkillType, Long> {
}
