package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PartyTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyType.class);
        PartyType partyType1 = new PartyType();
        partyType1.setId(1L);
        PartyType partyType2 = new PartyType();
        partyType2.setId(partyType1.getId());
        assertThat(partyType1).isEqualTo(partyType2);
        partyType2.setId(2L);
        assertThat(partyType1).isNotEqualTo(partyType2);
        partyType1.setId(null);
        assertThat(partyType1).isNotEqualTo(partyType2);
    }
}
