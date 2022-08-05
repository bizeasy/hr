package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class InvoiceItemTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceItemType.class);
        InvoiceItemType invoiceItemType1 = new InvoiceItemType();
        invoiceItemType1.setId(1L);
        InvoiceItemType invoiceItemType2 = new InvoiceItemType();
        invoiceItemType2.setId(invoiceItemType1.getId());
        assertThat(invoiceItemType1).isEqualTo(invoiceItemType2);
        invoiceItemType2.setId(2L);
        assertThat(invoiceItemType1).isNotEqualTo(invoiceItemType2);
        invoiceItemType1.setId(null);
        assertThat(invoiceItemType1).isNotEqualTo(invoiceItemType2);
    }
}
