package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class WorkEffortTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEffortType.class);
        WorkEffortType workEffortType1 = new WorkEffortType();
        workEffortType1.setId(1L);
        WorkEffortType workEffortType2 = new WorkEffortType();
        workEffortType2.setId(workEffortType1.getId());
        assertThat(workEffortType1).isEqualTo(workEffortType2);
        workEffortType2.setId(2L);
        assertThat(workEffortType1).isNotEqualTo(workEffortType2);
        workEffortType1.setId(null);
        assertThat(workEffortType1).isNotEqualTo(workEffortType2);
    }
}
