package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class OrderTermTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderTermType.class);
        OrderTermType orderTermType1 = new OrderTermType();
        orderTermType1.setId(1L);
        OrderTermType orderTermType2 = new OrderTermType();
        orderTermType2.setId(orderTermType1.getId());
        assertThat(orderTermType1).isEqualTo(orderTermType2);
        orderTermType2.setId(2L);
        assertThat(orderTermType1).isNotEqualTo(orderTermType2);
        orderTermType1.setId(null);
        assertThat(orderTermType1).isNotEqualTo(orderTermType2);
    }
}
