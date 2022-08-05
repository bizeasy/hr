package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class ProductFacilityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductFacility.class);
        ProductFacility productFacility1 = new ProductFacility();
        productFacility1.setId(1L);
        ProductFacility productFacility2 = new ProductFacility();
        productFacility2.setId(productFacility1.getId());
        assertThat(productFacility1).isEqualTo(productFacility2);
        productFacility2.setId(2L);
        assertThat(productFacility1).isNotEqualTo(productFacility2);
        productFacility1.setId(null);
        assertThat(productFacility1).isNotEqualTo(productFacility2);
    }
}
