package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class FacilityPartyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityParty.class);
        FacilityParty facilityParty1 = new FacilityParty();
        facilityParty1.setId(1L);
        FacilityParty facilityParty2 = new FacilityParty();
        facilityParty2.setId(facilityParty1.getId());
        assertThat(facilityParty1).isEqualTo(facilityParty2);
        facilityParty2.setId(2L);
        assertThat(facilityParty1).isNotEqualTo(facilityParty2);
        facilityParty1.setId(null);
        assertThat(facilityParty1).isNotEqualTo(facilityParty2);
    }
}
