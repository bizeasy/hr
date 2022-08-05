package com.hr.repository;

import com.hr.domain.PayGrade;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PayGrade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayGradeRepository extends JpaRepository<PayGrade, Long> {
}
