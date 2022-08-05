package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class WorkEffortTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEffort.class);
        WorkEffort workEffort1 = new WorkEffort();
        workEffort1.setId(1L);
        WorkEffort workEffort2 = new WorkEffort();
        workEffort2.setId(workEffort1.getId());
        assertThat(workEffort1).isEqualTo(workEffort2);
        workEffort2.setId(2L);
        assertThat(workEffort1).isNotEqualTo(workEffort2);
        workEffort1.setId(null);
        assertThat(workEffort1).isNotEqualTo(workEffort2);
    }
}
