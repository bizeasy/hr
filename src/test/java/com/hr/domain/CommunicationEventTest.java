package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class CommunicationEventTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommunicationEvent.class);
        CommunicationEvent communicationEvent1 = new CommunicationEvent();
        communicationEvent1.setId(1L);
        CommunicationEvent communicationEvent2 = new CommunicationEvent();
        communicationEvent2.setId(communicationEvent1.getId());
        assertThat(communicationEvent1).isEqualTo(communicationEvent2);
        communicationEvent2.setId(2L);
        assertThat(communicationEvent1).isNotEqualTo(communicationEvent2);
        communicationEvent1.setId(null);
        assertThat(communicationEvent1).isNotEqualTo(communicationEvent2);
    }
}
