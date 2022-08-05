package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class JobInterviewTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobInterviewType.class);
        JobInterviewType jobInterviewType1 = new JobInterviewType();
        jobInterviewType1.setId(1L);
        JobInterviewType jobInterviewType2 = new JobInterviewType();
        jobInterviewType2.setId(jobInterviewType1.getId());
        assertThat(jobInterviewType1).isEqualTo(jobInterviewType2);
        jobInterviewType2.setId(2L);
        assertThat(jobInterviewType1).isNotEqualTo(jobInterviewType2);
        jobInterviewType1.setId(null);
        assertThat(jobInterviewType1).isNotEqualTo(jobInterviewType2);
    }
}
