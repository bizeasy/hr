package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PartyRoleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyRole.class);
        PartyRole partyRole1 = new PartyRole();
        partyRole1.setId(1L);
        PartyRole partyRole2 = new PartyRole();
        partyRole2.setId(partyRole1.getId());
        assertThat(partyRole1).isEqualTo(partyRole2);
        partyRole2.setId(2L);
        assertThat(partyRole1).isNotEqualTo(partyRole2);
        partyRole1.setId(null);
        assertThat(partyRole1).isNotEqualTo(partyRole2);
    }
}
