package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class OrderItemTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderItemType.class);
        OrderItemType orderItemType1 = new OrderItemType();
        orderItemType1.setId(1L);
        OrderItemType orderItemType2 = new OrderItemType();
        orderItemType2.setId(orderItemType1.getId());
        assertThat(orderItemType1).isEqualTo(orderItemType2);
        orderItemType2.setId(2L);
        assertThat(orderItemType1).isNotEqualTo(orderItemType2);
        orderItemType1.setId(null);
        assertThat(orderItemType1).isNotEqualTo(orderItemType2);
    }
}
