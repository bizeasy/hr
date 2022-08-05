package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PartyClassificationTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyClassificationType.class);
        PartyClassificationType partyClassificationType1 = new PartyClassificationType();
        partyClassificationType1.setId(1L);
        PartyClassificationType partyClassificationType2 = new PartyClassificationType();
        partyClassificationType2.setId(partyClassificationType1.getId());
        assertThat(partyClassificationType1).isEqualTo(partyClassificationType2);
        partyClassificationType2.setId(2L);
        assertThat(partyClassificationType1).isNotEqualTo(partyClassificationType2);
        partyClassificationType1.setId(null);
        assertThat(partyClassificationType1).isNotEqualTo(partyClassificationType2);
    }
}
