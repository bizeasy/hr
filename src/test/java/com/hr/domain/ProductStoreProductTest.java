package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class ProductStoreProductTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductStoreProduct.class);
        ProductStoreProduct productStoreProduct1 = new ProductStoreProduct();
        productStoreProduct1.setId(1L);
        ProductStoreProduct productStoreProduct2 = new ProductStoreProduct();
        productStoreProduct2.setId(productStoreProduct1.getId());
        assertThat(productStoreProduct1).isEqualTo(productStoreProduct2);
        productStoreProduct2.setId(2L);
        assertThat(productStoreProduct1).isNotEqualTo(productStoreProduct2);
        productStoreProduct1.setId(null);
        assertThat(productStoreProduct1).isNotEqualTo(productStoreProduct2);
    }
}
