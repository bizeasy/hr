package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class WorkEffortInventoryProducedTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEffortInventoryProduced.class);
        WorkEffortInventoryProduced workEffortInventoryProduced1 = new WorkEffortInventoryProduced();
        workEffortInventoryProduced1.setId(1L);
        WorkEffortInventoryProduced workEffortInventoryProduced2 = new WorkEffortInventoryProduced();
        workEffortInventoryProduced2.setId(workEffortInventoryProduced1.getId());
        assertThat(workEffortInventoryProduced1).isEqualTo(workEffortInventoryProduced2);
        workEffortInventoryProduced2.setId(2L);
        assertThat(workEffortInventoryProduced1).isNotEqualTo(workEffortInventoryProduced2);
        workEffortInventoryProduced1.setId(null);
        assertThat(workEffortInventoryProduced1).isNotEqualTo(workEffortInventoryProduced2);
    }
}
