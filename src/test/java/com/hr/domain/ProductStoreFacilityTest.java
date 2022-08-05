package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class ProductStoreFacilityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductStoreFacility.class);
        ProductStoreFacility productStoreFacility1 = new ProductStoreFacility();
        productStoreFacility1.setId(1L);
        ProductStoreFacility productStoreFacility2 = new ProductStoreFacility();
        productStoreFacility2.setId(productStoreFacility1.getId());
        assertThat(productStoreFacility1).isEqualTo(productStoreFacility2);
        productStoreFacility2.setId(2L);
        assertThat(productStoreFacility1).isNotEqualTo(productStoreFacility2);
        productStoreFacility1.setId(null);
        assertThat(productStoreFacility1).isNotEqualTo(productStoreFacility2);
    }
}
