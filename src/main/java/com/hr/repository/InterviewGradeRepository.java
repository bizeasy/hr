package com.hr.repository;

import com.hr.domain.InterviewGrade;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InterviewGrade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterviewGradeRepository extends JpaRepository<InterviewGrade, Long> {
}
