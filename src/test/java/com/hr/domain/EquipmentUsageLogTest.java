package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class EquipmentUsageLogTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipmentUsageLog.class);
        EquipmentUsageLog equipmentUsageLog1 = new EquipmentUsageLog();
        equipmentUsageLog1.setId(1L);
        EquipmentUsageLog equipmentUsageLog2 = new EquipmentUsageLog();
        equipmentUsageLog2.setId(equipmentUsageLog1.getId());
        assertThat(equipmentUsageLog1).isEqualTo(equipmentUsageLog2);
        equipmentUsageLog2.setId(2L);
        assertThat(equipmentUsageLog1).isNotEqualTo(equipmentUsageLog2);
        equipmentUsageLog1.setId(null);
        assertThat(equipmentUsageLog1).isNotEqualTo(equipmentUsageLog2);
    }
}
