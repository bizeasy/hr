package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class ProductStoreUserGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductStoreUserGroup.class);
        ProductStoreUserGroup productStoreUserGroup1 = new ProductStoreUserGroup();
        productStoreUserGroup1.setId(1L);
        ProductStoreUserGroup productStoreUserGroup2 = new ProductStoreUserGroup();
        productStoreUserGroup2.setId(productStoreUserGroup1.getId());
        assertThat(productStoreUserGroup1).isEqualTo(productStoreUserGroup2);
        productStoreUserGroup2.setId(2L);
        assertThat(productStoreUserGroup1).isNotEqualTo(productStoreUserGroup2);
        productStoreUserGroup1.setId(null);
        assertThat(productStoreUserGroup1).isNotEqualTo(productStoreUserGroup2);
    }
}
