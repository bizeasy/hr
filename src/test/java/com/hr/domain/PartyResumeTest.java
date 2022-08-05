package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PartyResumeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyResume.class);
        PartyResume partyResume1 = new PartyResume();
        partyResume1.setId(1L);
        PartyResume partyResume2 = new PartyResume();
        partyResume2.setId(partyResume1.getId());
        assertThat(partyResume1).isEqualTo(partyResume2);
        partyResume2.setId(2L);
        assertThat(partyResume1).isNotEqualTo(partyResume2);
        partyResume1.setId(null);
        assertThat(partyResume1).isNotEqualTo(partyResume2);
    }
}
