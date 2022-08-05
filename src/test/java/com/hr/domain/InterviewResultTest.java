package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class InterviewResultTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterviewResult.class);
        InterviewResult interviewResult1 = new InterviewResult();
        interviewResult1.setId(1L);
        InterviewResult interviewResult2 = new InterviewResult();
        interviewResult2.setId(interviewResult1.getId());
        assertThat(interviewResult1).isEqualTo(interviewResult2);
        interviewResult2.setId(2L);
        assertThat(interviewResult1).isNotEqualTo(interviewResult2);
        interviewResult1.setId(null);
        assertThat(interviewResult1).isNotEqualTo(interviewResult2);
    }
}
