package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class ProductKeywordTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductKeyword.class);
        ProductKeyword productKeyword1 = new ProductKeyword();
        productKeyword1.setId(1L);
        ProductKeyword productKeyword2 = new ProductKeyword();
        productKeyword2.setId(productKeyword1.getId());
        assertThat(productKeyword1).isEqualTo(productKeyword2);
        productKeyword2.setId(2L);
        assertThat(productKeyword1).isNotEqualTo(productKeyword2);
        productKeyword1.setId(null);
        assertThat(productKeyword1).isNotEqualTo(productKeyword2);
    }
}
