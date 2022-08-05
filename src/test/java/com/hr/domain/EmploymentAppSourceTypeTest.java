package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class EmploymentAppSourceTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmploymentAppSourceType.class);
        EmploymentAppSourceType employmentAppSourceType1 = new EmploymentAppSourceType();
        employmentAppSourceType1.setId(1L);
        EmploymentAppSourceType employmentAppSourceType2 = new EmploymentAppSourceType();
        employmentAppSourceType2.setId(employmentAppSourceType1.getId());
        assertThat(employmentAppSourceType1).isEqualTo(employmentAppSourceType2);
        employmentAppSourceType2.setId(2L);
        assertThat(employmentAppSourceType1).isNotEqualTo(employmentAppSourceType2);
        employmentAppSourceType1.setId(null);
        assertThat(employmentAppSourceType1).isNotEqualTo(employmentAppSourceType2);
    }
}
