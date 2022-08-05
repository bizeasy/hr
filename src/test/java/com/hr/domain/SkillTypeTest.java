package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class SkillTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillType.class);
        SkillType skillType1 = new SkillType();
        skillType1.setId(1L);
        SkillType skillType2 = new SkillType();
        skillType2.setId(skillType1.getId());
        assertThat(skillType1).isEqualTo(skillType2);
        skillType2.setId(2L);
        assertThat(skillType1).isNotEqualTo(skillType2);
        skillType1.setId(null);
        assertThat(skillType1).isNotEqualTo(skillType2);
    }
}
