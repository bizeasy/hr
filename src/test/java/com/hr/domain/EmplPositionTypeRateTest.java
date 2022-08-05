package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class EmplPositionTypeRateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmplPositionTypeRate.class);
        EmplPositionTypeRate emplPositionTypeRate1 = new EmplPositionTypeRate();
        emplPositionTypeRate1.setId(1L);
        EmplPositionTypeRate emplPositionTypeRate2 = new EmplPositionTypeRate();
        emplPositionTypeRate2.setId(emplPositionTypeRate1.getId());
        assertThat(emplPositionTypeRate1).isEqualTo(emplPositionTypeRate2);
        emplPositionTypeRate2.setId(2L);
        assertThat(emplPositionTypeRate1).isNotEqualTo(emplPositionTypeRate2);
        emplPositionTypeRate1.setId(null);
        assertThat(emplPositionTypeRate1).isNotEqualTo(emplPositionTypeRate2);
    }
}
