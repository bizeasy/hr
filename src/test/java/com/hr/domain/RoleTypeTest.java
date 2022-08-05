package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class RoleTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleType.class);
        RoleType roleType1 = new RoleType();
        roleType1.setId(1L);
        RoleType roleType2 = new RoleType();
        roleType2.setId(roleType1.getId());
        assertThat(roleType1).isEqualTo(roleType2);
        roleType2.setId(2L);
        assertThat(roleType1).isNotEqualTo(roleType2);
        roleType1.setId(null);
        assertThat(roleType1).isNotEqualTo(roleType2);
    }
}
