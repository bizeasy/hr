package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class ReasonTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReasonType.class);
        ReasonType reasonType1 = new ReasonType();
        reasonType1.setId(1L);
        ReasonType reasonType2 = new ReasonType();
        reasonType2.setId(reasonType1.getId());
        assertThat(reasonType1).isEqualTo(reasonType2);
        reasonType2.setId(2L);
        assertThat(reasonType1).isNotEqualTo(reasonType2);
        reasonType1.setId(null);
        assertThat(reasonType1).isNotEqualTo(reasonType2);
    }
}
