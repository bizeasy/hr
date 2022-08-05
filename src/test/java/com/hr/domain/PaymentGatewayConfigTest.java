package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PaymentGatewayConfigTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentGatewayConfig.class);
        PaymentGatewayConfig paymentGatewayConfig1 = new PaymentGatewayConfig();
        paymentGatewayConfig1.setId(1L);
        PaymentGatewayConfig paymentGatewayConfig2 = new PaymentGatewayConfig();
        paymentGatewayConfig2.setId(paymentGatewayConfig1.getId());
        assertThat(paymentGatewayConfig1).isEqualTo(paymentGatewayConfig2);
        paymentGatewayConfig2.setId(2L);
        assertThat(paymentGatewayConfig1).isNotEqualTo(paymentGatewayConfig2);
        paymentGatewayConfig1.setId(null);
        assertThat(paymentGatewayConfig1).isNotEqualTo(paymentGatewayConfig2);
    }
}
