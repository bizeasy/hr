package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class HolidayTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HolidayType.class);
        HolidayType holidayType1 = new HolidayType();
        holidayType1.setId(1L);
        HolidayType holidayType2 = new HolidayType();
        holidayType2.setId(holidayType1.getId());
        assertThat(holidayType1).isEqualTo(holidayType2);
        holidayType2.setId(2L);
        assertThat(holidayType1).isNotEqualTo(holidayType2);
        holidayType1.setId(null);
        assertThat(holidayType1).isNotEqualTo(holidayType2);
    }
}
