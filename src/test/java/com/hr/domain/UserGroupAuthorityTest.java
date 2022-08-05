package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class UserGroupAuthorityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserGroupAuthority.class);
        UserGroupAuthority userGroupAuthority1 = new UserGroupAuthority();
        userGroupAuthority1.setId(1L);
        UserGroupAuthority userGroupAuthority2 = new UserGroupAuthority();
        userGroupAuthority2.setId(userGroupAuthority1.getId());
        assertThat(userGroupAuthority1).isEqualTo(userGroupAuthority2);
        userGroupAuthority2.setId(2L);
        assertThat(userGroupAuthority1).isNotEqualTo(userGroupAuthority2);
        userGroupAuthority1.setId(null);
        assertThat(userGroupAuthority1).isNotEqualTo(userGroupAuthority2);
    }
}
