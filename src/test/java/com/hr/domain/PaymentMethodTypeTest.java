package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PaymentMethodTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentMethodType.class);
        PaymentMethodType paymentMethodType1 = new PaymentMethodType();
        paymentMethodType1.setId(1L);
        PaymentMethodType paymentMethodType2 = new PaymentMethodType();
        paymentMethodType2.setId(paymentMethodType1.getId());
        assertThat(paymentMethodType1).isEqualTo(paymentMethodType2);
        paymentMethodType2.setId(2L);
        assertThat(paymentMethodType1).isNotEqualTo(paymentMethodType2);
        paymentMethodType1.setId(null);
        assertThat(paymentMethodType1).isNotEqualTo(paymentMethodType2);
    }
}
