package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PayGradeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayGrade.class);
        PayGrade payGrade1 = new PayGrade();
        payGrade1.setId(1L);
        PayGrade payGrade2 = new PayGrade();
        payGrade2.setId(payGrade1.getId());
        assertThat(payGrade1).isEqualTo(payGrade2);
        payGrade2.setId(2L);
        assertThat(payGrade1).isNotEqualTo(payGrade2);
        payGrade1.setId(null);
        assertThat(payGrade1).isNotEqualTo(payGrade2);
    }
}
