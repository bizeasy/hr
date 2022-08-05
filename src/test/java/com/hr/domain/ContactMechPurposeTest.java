package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class ContactMechPurposeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactMechPurpose.class);
        ContactMechPurpose contactMechPurpose1 = new ContactMechPurpose();
        contactMechPurpose1.setId(1L);
        ContactMechPurpose contactMechPurpose2 = new ContactMechPurpose();
        contactMechPurpose2.setId(contactMechPurpose1.getId());
        assertThat(contactMechPurpose1).isEqualTo(contactMechPurpose2);
        contactMechPurpose2.setId(2L);
        assertThat(contactMechPurpose1).isNotEqualTo(contactMechPurpose2);
        contactMechPurpose1.setId(null);
        assertThat(contactMechPurpose1).isNotEqualTo(contactMechPurpose2);
    }
}
