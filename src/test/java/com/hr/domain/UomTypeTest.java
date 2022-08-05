package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class UomTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UomType.class);
        UomType uomType1 = new UomType();
        uomType1.setId(1L);
        UomType uomType2 = new UomType();
        uomType2.setId(uomType1.getId());
        assertThat(uomType1).isEqualTo(uomType2);
        uomType2.setId(2L);
        assertThat(uomType1).isNotEqualTo(uomType2);
        uomType1.setId(null);
        assertThat(uomType1).isNotEqualTo(uomType2);
    }
}
