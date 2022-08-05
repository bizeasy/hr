package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PaymentApplicationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentApplication.class);
        PaymentApplication paymentApplication1 = new PaymentApplication();
        paymentApplication1.setId(1L);
        PaymentApplication paymentApplication2 = new PaymentApplication();
        paymentApplication2.setId(paymentApplication1.getId());
        assertThat(paymentApplication1).isEqualTo(paymentApplication2);
        paymentApplication2.setId(2L);
        assertThat(paymentApplication1).isNotEqualTo(paymentApplication2);
        paymentApplication1.setId(null);
        assertThat(paymentApplication1).isNotEqualTo(paymentApplication2);
    }
}
