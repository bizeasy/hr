package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PayrollPreferenceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayrollPreference.class);
        PayrollPreference payrollPreference1 = new PayrollPreference();
        payrollPreference1.setId(1L);
        PayrollPreference payrollPreference2 = new PayrollPreference();
        payrollPreference2.setId(payrollPreference1.getId());
        assertThat(payrollPreference1).isEqualTo(payrollPreference2);
        payrollPreference2.setId(2L);
        assertThat(payrollPreference1).isNotEqualTo(payrollPreference2);
        payrollPreference1.setId(null);
        assertThat(payrollPreference1).isNotEqualTo(payrollPreference2);
    }
}
