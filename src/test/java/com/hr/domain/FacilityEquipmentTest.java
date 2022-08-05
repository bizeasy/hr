package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class FacilityEquipmentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityEquipment.class);
        FacilityEquipment facilityEquipment1 = new FacilityEquipment();
        facilityEquipment1.setId(1L);
        FacilityEquipment facilityEquipment2 = new FacilityEquipment();
        facilityEquipment2.setId(facilityEquipment1.getId());
        assertThat(facilityEquipment1).isEqualTo(facilityEquipment2);
        facilityEquipment2.setId(2L);
        assertThat(facilityEquipment1).isNotEqualTo(facilityEquipment2);
        facilityEquipment1.setId(null);
        assertThat(facilityEquipment1).isNotEqualTo(facilityEquipment2);
    }
}
