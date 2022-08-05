package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PaymentGatewayResponseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentGatewayResponse.class);
        PaymentGatewayResponse paymentGatewayResponse1 = new PaymentGatewayResponse();
        paymentGatewayResponse1.setId(1L);
        PaymentGatewayResponse paymentGatewayResponse2 = new PaymentGatewayResponse();
        paymentGatewayResponse2.setId(paymentGatewayResponse1.getId());
        assertThat(paymentGatewayResponse1).isEqualTo(paymentGatewayResponse2);
        paymentGatewayResponse2.setId(2L);
        assertThat(paymentGatewayResponse1).isNotEqualTo(paymentGatewayResponse2);
        paymentGatewayResponse1.setId(null);
        assertThat(paymentGatewayResponse1).isNotEqualTo(paymentGatewayResponse2);
    }
}
