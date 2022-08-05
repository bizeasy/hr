package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class OrderTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderType.class);
        OrderType orderType1 = new OrderType();
        orderType1.setId(1L);
        OrderType orderType2 = new OrderType();
        orderType2.setId(orderType1.getId());
        assertThat(orderType1).isEqualTo(orderType2);
        orderType2.setId(2L);
        assertThat(orderType1).isNotEqualTo(orderType2);
        orderType1.setId(null);
        assertThat(orderType1).isNotEqualTo(orderType2);
    }
}
