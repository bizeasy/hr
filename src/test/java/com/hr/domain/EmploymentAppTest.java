package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class EmploymentAppTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmploymentApp.class);
        EmploymentApp employmentApp1 = new EmploymentApp();
        employmentApp1.setId(1L);
        EmploymentApp employmentApp2 = new EmploymentApp();
        employmentApp2.setId(employmentApp1.getId());
        assertThat(employmentApp1).isEqualTo(employmentApp2);
        employmentApp2.setId(2L);
        assertThat(employmentApp1).isNotEqualTo(employmentApp2);
        employmentApp1.setId(null);
        assertThat(employmentApp1).isNotEqualTo(employmentApp2);
    }
}
