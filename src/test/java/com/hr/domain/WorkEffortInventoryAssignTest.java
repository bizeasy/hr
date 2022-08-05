package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class WorkEffortInventoryAssignTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEffortInventoryAssign.class);
        WorkEffortInventoryAssign workEffortInventoryAssign1 = new WorkEffortInventoryAssign();
        workEffortInventoryAssign1.setId(1L);
        WorkEffortInventoryAssign workEffortInventoryAssign2 = new WorkEffortInventoryAssign();
        workEffortInventoryAssign2.setId(workEffortInventoryAssign1.getId());
        assertThat(workEffortInventoryAssign1).isEqualTo(workEffortInventoryAssign2);
        workEffortInventoryAssign2.setId(2L);
        assertThat(workEffortInventoryAssign1).isNotEqualTo(workEffortInventoryAssign2);
        workEffortInventoryAssign1.setId(null);
        assertThat(workEffortInventoryAssign1).isNotEqualTo(workEffortInventoryAssign2);
    }
}
