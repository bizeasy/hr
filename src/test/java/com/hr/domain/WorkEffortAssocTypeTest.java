package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class WorkEffortAssocTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEffortAssocType.class);
        WorkEffortAssocType workEffortAssocType1 = new WorkEffortAssocType();
        workEffortAssocType1.setId(1L);
        WorkEffortAssocType workEffortAssocType2 = new WorkEffortAssocType();
        workEffortAssocType2.setId(workEffortAssocType1.getId());
        assertThat(workEffortAssocType1).isEqualTo(workEffortAssocType2);
        workEffortAssocType2.setId(2L);
        assertThat(workEffortAssocType1).isNotEqualTo(workEffortAssocType2);
        workEffortAssocType1.setId(null);
        assertThat(workEffortAssocType1).isNotEqualTo(workEffortAssocType2);
    }
}
