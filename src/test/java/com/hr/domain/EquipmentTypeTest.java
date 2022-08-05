package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class EquipmentTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipmentType.class);
        EquipmentType equipmentType1 = new EquipmentType();
        equipmentType1.setId(1L);
        EquipmentType equipmentType2 = new EquipmentType();
        equipmentType2.setId(equipmentType1.getId());
        assertThat(equipmentType1).isEqualTo(equipmentType2);
        equipmentType2.setId(2L);
        assertThat(equipmentType1).isNotEqualTo(equipmentType2);
        equipmentType1.setId(null);
        assertThat(equipmentType1).isNotEqualTo(equipmentType2);
    }
}
