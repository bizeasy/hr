package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class EmplPositionGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmplPositionGroup.class);
        EmplPositionGroup emplPositionGroup1 = new EmplPositionGroup();
        emplPositionGroup1.setId(1L);
        EmplPositionGroup emplPositionGroup2 = new EmplPositionGroup();
        emplPositionGroup2.setId(emplPositionGroup1.getId());
        assertThat(emplPositionGroup1).isEqualTo(emplPositionGroup2);
        emplPositionGroup2.setId(2L);
        assertThat(emplPositionGroup1).isNotEqualTo(emplPositionGroup2);
        emplPositionGroup1.setId(null);
        assertThat(emplPositionGroup1).isNotEqualTo(emplPositionGroup2);
    }
}
