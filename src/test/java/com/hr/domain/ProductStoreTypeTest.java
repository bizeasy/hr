package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class ProductStoreTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductStoreType.class);
        ProductStoreType productStoreType1 = new ProductStoreType();
        productStoreType1.setId(1L);
        ProductStoreType productStoreType2 = new ProductStoreType();
        productStoreType2.setId(productStoreType1.getId());
        assertThat(productStoreType1).isEqualTo(productStoreType2);
        productStoreType2.setId(2L);
        assertThat(productStoreType1).isNotEqualTo(productStoreType2);
        productStoreType1.setId(null);
        assertThat(productStoreType1).isNotEqualTo(productStoreType2);
    }
}
