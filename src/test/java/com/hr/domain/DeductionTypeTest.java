package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class DeductionTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeductionType.class);
        DeductionType deductionType1 = new DeductionType();
        deductionType1.setId(1L);
        DeductionType deductionType2 = new DeductionType();
        deductionType2.setId(deductionType1.getId());
        assertThat(deductionType1).isEqualTo(deductionType2);
        deductionType2.setId(2L);
        assertThat(deductionType1).isNotEqualTo(deductionType2);
        deductionType1.setId(null);
        assertThat(deductionType1).isNotEqualTo(deductionType2);
    }
}
