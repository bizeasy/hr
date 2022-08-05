package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class EmplPositionReportingStructTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmplPositionReportingStruct.class);
        EmplPositionReportingStruct emplPositionReportingStruct1 = new EmplPositionReportingStruct();
        emplPositionReportingStruct1.setId(1L);
        EmplPositionReportingStruct emplPositionReportingStruct2 = new EmplPositionReportingStruct();
        emplPositionReportingStruct2.setId(emplPositionReportingStruct1.getId());
        assertThat(emplPositionReportingStruct1).isEqualTo(emplPositionReportingStruct2);
        emplPositionReportingStruct2.setId(2L);
        assertThat(emplPositionReportingStruct1).isNotEqualTo(emplPositionReportingStruct2);
        emplPositionReportingStruct1.setId(null);
        assertThat(emplPositionReportingStruct1).isNotEqualTo(emplPositionReportingStruct2);
    }
}
