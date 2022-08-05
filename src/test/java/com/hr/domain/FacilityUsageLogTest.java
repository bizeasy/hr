package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class FacilityUsageLogTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityUsageLog.class);
        FacilityUsageLog facilityUsageLog1 = new FacilityUsageLog();
        facilityUsageLog1.setId(1L);
        FacilityUsageLog facilityUsageLog2 = new FacilityUsageLog();
        facilityUsageLog2.setId(facilityUsageLog1.getId());
        assertThat(facilityUsageLog1).isEqualTo(facilityUsageLog2);
        facilityUsageLog2.setId(2L);
        assertThat(facilityUsageLog1).isNotEqualTo(facilityUsageLog2);
        facilityUsageLog1.setId(null);
        assertThat(facilityUsageLog1).isNotEqualTo(facilityUsageLog2);
    }
}
