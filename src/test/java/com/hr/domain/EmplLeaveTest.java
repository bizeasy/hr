package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class EmplLeaveTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmplLeave.class);
        EmplLeave emplLeave1 = new EmplLeave();
        emplLeave1.setId(1L);
        EmplLeave emplLeave2 = new EmplLeave();
        emplLeave2.setId(emplLeave1.getId());
        assertThat(emplLeave1).isEqualTo(emplLeave2);
        emplLeave2.setId(2L);
        assertThat(emplLeave1).isNotEqualTo(emplLeave2);
        emplLeave1.setId(null);
        assertThat(emplLeave1).isNotEqualTo(emplLeave2);
    }
}
