package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class WorkEffortProductTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEffortProduct.class);
        WorkEffortProduct workEffortProduct1 = new WorkEffortProduct();
        workEffortProduct1.setId(1L);
        WorkEffortProduct workEffortProduct2 = new WorkEffortProduct();
        workEffortProduct2.setId(workEffortProduct1.getId());
        assertThat(workEffortProduct1).isEqualTo(workEffortProduct2);
        workEffortProduct2.setId(2L);
        assertThat(workEffortProduct1).isNotEqualTo(workEffortProduct2);
        workEffortProduct1.setId(null);
        assertThat(workEffortProduct1).isNotEqualTo(workEffortProduct2);
    }
}
