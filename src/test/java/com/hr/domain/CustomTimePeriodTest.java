package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class CustomTimePeriodTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomTimePeriod.class);
        CustomTimePeriod customTimePeriod1 = new CustomTimePeriod();
        customTimePeriod1.setId(1L);
        CustomTimePeriod customTimePeriod2 = new CustomTimePeriod();
        customTimePeriod2.setId(customTimePeriod1.getId());
        assertThat(customTimePeriod1).isEqualTo(customTimePeriod2);
        customTimePeriod2.setId(2L);
        assertThat(customTimePeriod1).isNotEqualTo(customTimePeriod2);
        customTimePeriod1.setId(null);
        assertThat(customTimePeriod1).isNotEqualTo(customTimePeriod2);
    }
}
