package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class EmplLeaveTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmplLeaveType.class);
        EmplLeaveType emplLeaveType1 = new EmplLeaveType();
        emplLeaveType1.setId(1L);
        EmplLeaveType emplLeaveType2 = new EmplLeaveType();
        emplLeaveType2.setId(emplLeaveType1.getId());
        assertThat(emplLeaveType1).isEqualTo(emplLeaveType2);
        emplLeaveType2.setId(2L);
        assertThat(emplLeaveType1).isNotEqualTo(emplLeaveType2);
        emplLeaveType1.setId(null);
        assertThat(emplLeaveType1).isNotEqualTo(emplLeaveType2);
    }
}
