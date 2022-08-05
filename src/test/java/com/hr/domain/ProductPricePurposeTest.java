package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class ProductPricePurposeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductPricePurpose.class);
        ProductPricePurpose productPricePurpose1 = new ProductPricePurpose();
        productPricePurpose1.setId(1L);
        ProductPricePurpose productPricePurpose2 = new ProductPricePurpose();
        productPricePurpose2.setId(productPricePurpose1.getId());
        assertThat(productPricePurpose1).isEqualTo(productPricePurpose2);
        productPricePurpose2.setId(2L);
        assertThat(productPricePurpose1).isNotEqualTo(productPricePurpose2);
        productPricePurpose1.setId(null);
        assertThat(productPricePurpose1).isNotEqualTo(productPricePurpose2);
    }
}
