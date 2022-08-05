package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class FacilityGroupMemberTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityGroupMember.class);
        FacilityGroupMember facilityGroupMember1 = new FacilityGroupMember();
        facilityGroupMember1.setId(1L);
        FacilityGroupMember facilityGroupMember2 = new FacilityGroupMember();
        facilityGroupMember2.setId(facilityGroupMember1.getId());
        assertThat(facilityGroupMember1).isEqualTo(facilityGroupMember2);
        facilityGroupMember2.setId(2L);
        assertThat(facilityGroupMember1).isNotEqualTo(facilityGroupMember2);
        facilityGroupMember1.setId(null);
        assertThat(facilityGroupMember1).isNotEqualTo(facilityGroupMember2);
    }
}
