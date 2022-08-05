package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class TermTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TermType.class);
        TermType termType1 = new TermType();
        termType1.setId(1L);
        TermType termType2 = new TermType();
        termType2.setId(termType1.getId());
        assertThat(termType1).isEqualTo(termType2);
        termType2.setId(2L);
        assertThat(termType1).isNotEqualTo(termType2);
        termType1.setId(null);
        assertThat(termType1).isNotEqualTo(termType2);
    }
}
