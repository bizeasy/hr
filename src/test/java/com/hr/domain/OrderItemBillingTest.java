package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class OrderItemBillingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderItemBilling.class);
        OrderItemBilling orderItemBilling1 = new OrderItemBilling();
        orderItemBilling1.setId(1L);
        OrderItemBilling orderItemBilling2 = new OrderItemBilling();
        orderItemBilling2.setId(orderItemBilling1.getId());
        assertThat(orderItemBilling1).isEqualTo(orderItemBilling2);
        orderItemBilling2.setId(2L);
        assertThat(orderItemBilling1).isNotEqualTo(orderItemBilling2);
        orderItemBilling1.setId(null);
        assertThat(orderItemBilling1).isNotEqualTo(orderItemBilling2);
    }
}
