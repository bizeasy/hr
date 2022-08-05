package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class EmplPositionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmplPosition.class);
        EmplPosition emplPosition1 = new EmplPosition();
        emplPosition1.setId(1L);
        EmplPosition emplPosition2 = new EmplPosition();
        emplPosition2.setId(emplPosition1.getId());
        assertThat(emplPosition1).isEqualTo(emplPosition2);
        emplPosition2.setId(2L);
        assertThat(emplPosition1).isNotEqualTo(emplPosition2);
        emplPosition1.setId(null);
        assertThat(emplPosition1).isNotEqualTo(emplPosition2);
    }
}
