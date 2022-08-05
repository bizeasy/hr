package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class TaxAuthorityRateTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxAuthorityRateType.class);
        TaxAuthorityRateType taxAuthorityRateType1 = new TaxAuthorityRateType();
        taxAuthorityRateType1.setId(1L);
        TaxAuthorityRateType taxAuthorityRateType2 = new TaxAuthorityRateType();
        taxAuthorityRateType2.setId(taxAuthorityRateType1.getId());
        assertThat(taxAuthorityRateType1).isEqualTo(taxAuthorityRateType2);
        taxAuthorityRateType2.setId(2L);
        assertThat(taxAuthorityRateType1).isNotEqualTo(taxAuthorityRateType2);
        taxAuthorityRateType1.setId(null);
        assertThat(taxAuthorityRateType1).isNotEqualTo(taxAuthorityRateType2);
    }
}
