package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class JobRequisitionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobRequisition.class);
        JobRequisition jobRequisition1 = new JobRequisition();
        jobRequisition1.setId(1L);
        JobRequisition jobRequisition2 = new JobRequisition();
        jobRequisition2.setId(jobRequisition1.getId());
        assertThat(jobRequisition1).isEqualTo(jobRequisition2);
        jobRequisition2.setId(2L);
        assertThat(jobRequisition1).isNotEqualTo(jobRequisition2);
        jobRequisition1.setId(null);
        assertThat(jobRequisition1).isNotEqualTo(jobRequisition2);
    }
}
