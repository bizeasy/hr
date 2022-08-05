package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PartyContactMechTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyContactMech.class);
        PartyContactMech partyContactMech1 = new PartyContactMech();
        partyContactMech1.setId(1L);
        PartyContactMech partyContactMech2 = new PartyContactMech();
        partyContactMech2.setId(partyContactMech1.getId());
        assertThat(partyContactMech1).isEqualTo(partyContactMech2);
        partyContactMech2.setId(2L);
        assertThat(partyContactMech1).isNotEqualTo(partyContactMech2);
        partyContactMech1.setId(null);
        assertThat(partyContactMech1).isNotEqualTo(partyContactMech2);
    }
}
