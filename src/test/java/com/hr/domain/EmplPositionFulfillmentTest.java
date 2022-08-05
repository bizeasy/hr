package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class EmplPositionFulfillmentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmplPositionFulfillment.class);
        EmplPositionFulfillment emplPositionFulfillment1 = new EmplPositionFulfillment();
        emplPositionFulfillment1.setId(1L);
        EmplPositionFulfillment emplPositionFulfillment2 = new EmplPositionFulfillment();
        emplPositionFulfillment2.setId(emplPositionFulfillment1.getId());
        assertThat(emplPositionFulfillment1).isEqualTo(emplPositionFulfillment2);
        emplPositionFulfillment2.setId(2L);
        assertThat(emplPositionFulfillment1).isNotEqualTo(emplPositionFulfillment2);
        emplPositionFulfillment1.setId(null);
        assertThat(emplPositionFulfillment1).isNotEqualTo(emplPositionFulfillment2);
    }
}
