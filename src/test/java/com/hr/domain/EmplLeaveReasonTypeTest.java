package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class EmplLeaveReasonTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmplLeaveReasonType.class);
        EmplLeaveReasonType emplLeaveReasonType1 = new EmplLeaveReasonType();
        emplLeaveReasonType1.setId(1L);
        EmplLeaveReasonType emplLeaveReasonType2 = new EmplLeaveReasonType();
        emplLeaveReasonType2.setId(emplLeaveReasonType1.getId());
        assertThat(emplLeaveReasonType1).isEqualTo(emplLeaveReasonType2);
        emplLeaveReasonType2.setId(2L);
        assertThat(emplLeaveReasonType1).isNotEqualTo(emplLeaveReasonType2);
        emplLeaveReasonType1.setId(null);
        assertThat(emplLeaveReasonType1).isNotEqualTo(emplLeaveReasonType2);
    }
}
