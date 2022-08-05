package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PublicHolidaysTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicHolidays.class);
        PublicHolidays publicHolidays1 = new PublicHolidays();
        publicHolidays1.setId(1L);
        PublicHolidays publicHolidays2 = new PublicHolidays();
        publicHolidays2.setId(publicHolidays1.getId());
        assertThat(publicHolidays1).isEqualTo(publicHolidays2);
        publicHolidays2.setId(2L);
        assertThat(publicHolidays1).isNotEqualTo(publicHolidays2);
        publicHolidays1.setId(null);
        assertThat(publicHolidays1).isNotEqualTo(publicHolidays2);
    }
}
