package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class ShiftHolidaysTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShiftHolidays.class);
        ShiftHolidays shiftHolidays1 = new ShiftHolidays();
        shiftHolidays1.setId(1L);
        ShiftHolidays shiftHolidays2 = new ShiftHolidays();
        shiftHolidays2.setId(shiftHolidays1.getId());
        assertThat(shiftHolidays1).isEqualTo(shiftHolidays2);
        shiftHolidays2.setId(2L);
        assertThat(shiftHolidays1).isNotEqualTo(shiftHolidays2);
        shiftHolidays1.setId(null);
        assertThat(shiftHolidays1).isNotEqualTo(shiftHolidays2);
    }
}
