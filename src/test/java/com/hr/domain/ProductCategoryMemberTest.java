package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class ProductCategoryMemberTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCategoryMember.class);
        ProductCategoryMember productCategoryMember1 = new ProductCategoryMember();
        productCategoryMember1.setId(1L);
        ProductCategoryMember productCategoryMember2 = new ProductCategoryMember();
        productCategoryMember2.setId(productCategoryMember1.getId());
        assertThat(productCategoryMember1).isEqualTo(productCategoryMember2);
        productCategoryMember2.setId(2L);
        assertThat(productCategoryMember1).isNotEqualTo(productCategoryMember2);
        productCategoryMember1.setId(null);
        assertThat(productCategoryMember1).isNotEqualTo(productCategoryMember2);
    }
}
