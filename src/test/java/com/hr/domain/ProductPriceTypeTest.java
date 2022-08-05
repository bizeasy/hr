package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class ProductPriceTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductPriceType.class);
        ProductPriceType productPriceType1 = new ProductPriceType();
        productPriceType1.setId(1L);
        ProductPriceType productPriceType2 = new ProductPriceType();
        productPriceType2.setId(productPriceType1.getId());
        assertThat(productPriceType1).isEqualTo(productPriceType2);
        productPriceType2.setId(2L);
        assertThat(productPriceType1).isNotEqualTo(productPriceType2);
        productPriceType1.setId(null);
        assertThat(productPriceType1).isNotEqualTo(productPriceType2);
    }
}
