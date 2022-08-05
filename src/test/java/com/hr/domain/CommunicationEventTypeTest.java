package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class CommunicationEventTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommunicationEventType.class);
        CommunicationEventType communicationEventType1 = new CommunicationEventType();
        communicationEventType1.setId(1L);
        CommunicationEventType communicationEventType2 = new CommunicationEventType();
        communicationEventType2.setId(communicationEventType1.getId());
        assertThat(communicationEventType1).isEqualTo(communicationEventType2);
        communicationEventType2.setId(2L);
        assertThat(communicationEventType1).isNotEqualTo(communicationEventType2);
        communicationEventType1.setId(null);
        assertThat(communicationEventType1).isNotEqualTo(communicationEventType2);
    }
}
