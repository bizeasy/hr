package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PartyClassificationGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyClassificationGroup.class);
        PartyClassificationGroup partyClassificationGroup1 = new PartyClassificationGroup();
        partyClassificationGroup1.setId(1L);
        PartyClassificationGroup partyClassificationGroup2 = new PartyClassificationGroup();
        partyClassificationGroup2.setId(partyClassificationGroup1.getId());
        assertThat(partyClassificationGroup1).isEqualTo(partyClassificationGroup2);
        partyClassificationGroup2.setId(2L);
        assertThat(partyClassificationGroup1).isNotEqualTo(partyClassificationGroup2);
        partyClassificationGroup1.setId(null);
        assertThat(partyClassificationGroup1).isNotEqualTo(partyClassificationGroup2);
    }
}
