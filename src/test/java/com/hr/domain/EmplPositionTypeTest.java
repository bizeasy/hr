package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class EmplPositionTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmplPositionType.class);
        EmplPositionType emplPositionType1 = new EmplPositionType();
        emplPositionType1.setId(1L);
        EmplPositionType emplPositionType2 = new EmplPositionType();
        emplPositionType2.setId(emplPositionType1.getId());
        assertThat(emplPositionType1).isEqualTo(emplPositionType2);
        emplPositionType2.setId(2L);
        assertThat(emplPositionType1).isNotEqualTo(emplPositionType2);
        emplPositionType1.setId(null);
        assertThat(emplPositionType1).isNotEqualTo(emplPositionType2);
    }
}
