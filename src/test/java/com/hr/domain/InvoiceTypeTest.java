package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class InvoiceTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceType.class);
        InvoiceType invoiceType1 = new InvoiceType();
        invoiceType1.setId(1L);
        InvoiceType invoiceType2 = new InvoiceType();
        invoiceType2.setId(invoiceType1.getId());
        assertThat(invoiceType1).isEqualTo(invoiceType2);
        invoiceType2.setId(2L);
        assertThat(invoiceType1).isNotEqualTo(invoiceType2);
        invoiceType1.setId(null);
        assertThat(invoiceType1).isNotEqualTo(invoiceType2);
    }
}
