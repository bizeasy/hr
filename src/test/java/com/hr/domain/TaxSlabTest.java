package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class TaxSlabTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxSlab.class);
        TaxSlab taxSlab1 = new TaxSlab();
        taxSlab1.setId(1L);
        TaxSlab taxSlab2 = new TaxSlab();
        taxSlab2.setId(taxSlab1.getId());
        assertThat(taxSlab1).isEqualTo(taxSlab2);
        taxSlab2.setId(2L);
        assertThat(taxSlab1).isNotEqualTo(taxSlab2);
        taxSlab1.setId(null);
        assertThat(taxSlab1).isNotEqualTo(taxSlab2);
    }
}
