package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PermissionAuthorityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PermissionAuthority.class);
        PermissionAuthority permissionAuthority1 = new PermissionAuthority();
        permissionAuthority1.setId(1L);
        PermissionAuthority permissionAuthority2 = new PermissionAuthority();
        permissionAuthority2.setId(permissionAuthority1.getId());
        assertThat(permissionAuthority1).isEqualTo(permissionAuthority2);
        permissionAuthority2.setId(2L);
        assertThat(permissionAuthority1).isNotEqualTo(permissionAuthority2);
        permissionAuthority1.setId(null);
        assertThat(permissionAuthority1).isNotEqualTo(permissionAuthority2);
    }
}
