package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class OrderTermTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderTerm.class);
        OrderTerm orderTerm1 = new OrderTerm();
        orderTerm1.setId(1L);
        OrderTerm orderTerm2 = new OrderTerm();
        orderTerm2.setId(orderTerm1.getId());
        assertThat(orderTerm1).isEqualTo(orderTerm2);
        orderTerm2.setId(2L);
        assertThat(orderTerm1).isNotEqualTo(orderTerm2);
        orderTerm1.setId(null);
        assertThat(orderTerm1).isNotEqualTo(orderTerm2);
    }
}
