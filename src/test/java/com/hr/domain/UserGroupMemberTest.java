package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class UserGroupMemberTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserGroupMember.class);
        UserGroupMember userGroupMember1 = new UserGroupMember();
        userGroupMember1.setId(1L);
        UserGroupMember userGroupMember2 = new UserGroupMember();
        userGroupMember2.setId(userGroupMember1.getId());
        assertThat(userGroupMember1).isEqualTo(userGroupMember2);
        userGroupMember2.setId(2L);
        assertThat(userGroupMember1).isNotEqualTo(userGroupMember2);
        userGroupMember1.setId(null);
        assertThat(userGroupMember1).isNotEqualTo(userGroupMember2);
    }
}
