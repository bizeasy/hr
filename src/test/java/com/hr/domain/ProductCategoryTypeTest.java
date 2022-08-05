package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class ProductCategoryTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCategoryType.class);
        ProductCategoryType productCategoryType1 = new ProductCategoryType();
        productCategoryType1.setId(1L);
        ProductCategoryType productCategoryType2 = new ProductCategoryType();
        productCategoryType2.setId(productCategoryType1.getId());
        assertThat(productCategoryType1).isEqualTo(productCategoryType2);
        productCategoryType2.setId(2L);
        assertThat(productCategoryType1).isNotEqualTo(productCategoryType2);
        productCategoryType1.setId(null);
        assertThat(productCategoryType1).isNotEqualTo(productCategoryType2);
    }
}
