package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class JobInterviewTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobInterview.class);
        JobInterview jobInterview1 = new JobInterview();
        jobInterview1.setId(1L);
        JobInterview jobInterview2 = new JobInterview();
        jobInterview2.setId(jobInterview1.getId());
        assertThat(jobInterview1).isEqualTo(jobInterview2);
        jobInterview2.setId(2L);
        assertThat(jobInterview1).isNotEqualTo(jobInterview2);
        jobInterview1.setId(null);
        assertThat(jobInterview1).isNotEqualTo(jobInterview2);
    }
}
