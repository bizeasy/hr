package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class WorkEffortAssocTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEffortAssoc.class);
        WorkEffortAssoc workEffortAssoc1 = new WorkEffortAssoc();
        workEffortAssoc1.setId(1L);
        WorkEffortAssoc workEffortAssoc2 = new WorkEffortAssoc();
        workEffortAssoc2.setId(workEffortAssoc1.getId());
        assertThat(workEffortAssoc1).isEqualTo(workEffortAssoc2);
        workEffortAssoc2.setId(2L);
        assertThat(workEffortAssoc1).isNotEqualTo(workEffortAssoc2);
        workEffortAssoc1.setId(null);
        assertThat(workEffortAssoc1).isNotEqualTo(workEffortAssoc2);
    }
}
