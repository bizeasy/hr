package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class OrderContactMechTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderContactMech.class);
        OrderContactMech orderContactMech1 = new OrderContactMech();
        orderContactMech1.setId(1L);
        OrderContactMech orderContactMech2 = new OrderContactMech();
        orderContactMech2.setId(orderContactMech1.getId());
        assertThat(orderContactMech1).isEqualTo(orderContactMech2);
        orderContactMech2.setId(2L);
        assertThat(orderContactMech1).isNotEqualTo(orderContactMech2);
        orderContactMech1.setId(null);
        assertThat(orderContactMech1).isNotEqualTo(orderContactMech2);
    }
}
