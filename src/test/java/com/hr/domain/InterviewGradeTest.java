package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class InterviewGradeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterviewGrade.class);
        InterviewGrade interviewGrade1 = new InterviewGrade();
        interviewGrade1.setId(1L);
        InterviewGrade interviewGrade2 = new InterviewGrade();
        interviewGrade2.setId(interviewGrade1.getId());
        assertThat(interviewGrade1).isEqualTo(interviewGrade2);
        interviewGrade2.setId(2L);
        assertThat(interviewGrade1).isNotEqualTo(interviewGrade2);
        interviewGrade1.setId(null);
        assertThat(interviewGrade1).isNotEqualTo(interviewGrade2);
    }
}
