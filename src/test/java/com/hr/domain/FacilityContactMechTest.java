package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class FacilityContactMechTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityContactMech.class);
        FacilityContactMech facilityContactMech1 = new FacilityContactMech();
        facilityContactMech1.setId(1L);
        FacilityContactMech facilityContactMech2 = new FacilityContactMech();
        facilityContactMech2.setId(facilityContactMech1.getId());
        assertThat(facilityContactMech1).isEqualTo(facilityContactMech2);
        facilityContactMech2.setId(2L);
        assertThat(facilityContactMech1).isNotEqualTo(facilityContactMech2);
        facilityContactMech1.setId(null);
        assertThat(facilityContactMech1).isNotEqualTo(facilityContactMech2);
    }
}
