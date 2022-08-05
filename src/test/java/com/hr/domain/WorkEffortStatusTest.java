package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class WorkEffortStatusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEffortStatus.class);
        WorkEffortStatus workEffortStatus1 = new WorkEffortStatus();
        workEffortStatus1.setId(1L);
        WorkEffortStatus workEffortStatus2 = new WorkEffortStatus();
        workEffortStatus2.setId(workEffortStatus1.getId());
        assertThat(workEffortStatus1).isEqualTo(workEffortStatus2);
        workEffortStatus2.setId(2L);
        assertThat(workEffortStatus1).isNotEqualTo(workEffortStatus2);
        workEffortStatus1.setId(null);
        assertThat(workEffortStatus1).isNotEqualTo(workEffortStatus2);
    }
}
