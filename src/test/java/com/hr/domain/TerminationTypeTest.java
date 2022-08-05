package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class TerminationTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TerminationType.class);
        TerminationType terminationType1 = new TerminationType();
        terminationType1.setId(1L);
        TerminationType terminationType2 = new TerminationType();
        terminationType2.setId(terminationType1.getId());
        assertThat(terminationType1).isEqualTo(terminationType2);
        terminationType2.setId(2L);
        assertThat(terminationType1).isNotEqualTo(terminationType2);
        terminationType1.setId(null);
        assertThat(terminationType1).isNotEqualTo(terminationType2);
    }
}
