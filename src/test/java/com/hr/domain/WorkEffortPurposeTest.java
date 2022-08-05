package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class WorkEffortPurposeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEffortPurpose.class);
        WorkEffortPurpose workEffortPurpose1 = new WorkEffortPurpose();
        workEffortPurpose1.setId(1L);
        WorkEffortPurpose workEffortPurpose2 = new WorkEffortPurpose();
        workEffortPurpose2.setId(workEffortPurpose1.getId());
        assertThat(workEffortPurpose1).isEqualTo(workEffortPurpose2);
        workEffortPurpose2.setId(2L);
        assertThat(workEffortPurpose1).isNotEqualTo(workEffortPurpose2);
        workEffortPurpose1.setId(null);
        assertThat(workEffortPurpose1).isNotEqualTo(workEffortPurpose2);
    }
}
