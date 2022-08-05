package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class ShiftWeekendsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShiftWeekends.class);
        ShiftWeekends shiftWeekends1 = new ShiftWeekends();
        shiftWeekends1.setId(1L);
        ShiftWeekends shiftWeekends2 = new ShiftWeekends();
        shiftWeekends2.setId(shiftWeekends1.getId());
        assertThat(shiftWeekends1).isEqualTo(shiftWeekends2);
        shiftWeekends2.setId(2L);
        assertThat(shiftWeekends1).isNotEqualTo(shiftWeekends2);
        shiftWeekends1.setId(null);
        assertThat(shiftWeekends1).isNotEqualTo(shiftWeekends2);
    }
}
