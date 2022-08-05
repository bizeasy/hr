package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class TaxAuthorityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxAuthority.class);
        TaxAuthority taxAuthority1 = new TaxAuthority();
        taxAuthority1.setId(1L);
        TaxAuthority taxAuthority2 = new TaxAuthority();
        taxAuthority2.setId(taxAuthority1.getId());
        assertThat(taxAuthority1).isEqualTo(taxAuthority2);
        taxAuthority2.setId(2L);
        assertThat(taxAuthority1).isNotEqualTo(taxAuthority2);
        taxAuthority1.setId(null);
        assertThat(taxAuthority1).isNotEqualTo(taxAuthority2);
    }
}
