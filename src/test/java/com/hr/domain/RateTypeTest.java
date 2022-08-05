package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class RateTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RateType.class);
        RateType rateType1 = new RateType();
        rateType1.setId(1L);
        RateType rateType2 = new RateType();
        rateType2.setId(rateType1.getId());
        assertThat(rateType1).isEqualTo(rateType2);
        rateType2.setId(2L);
        assertThat(rateType1).isNotEqualTo(rateType2);
        rateType1.setId(null);
        assertThat(rateType1).isNotEqualTo(rateType2);
    }
}
