package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class ContactMechTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactMechType.class);
        ContactMechType contactMechType1 = new ContactMechType();
        contactMechType1.setId(1L);
        ContactMechType contactMechType2 = new ContactMechType();
        contactMechType2.setId(contactMechType1.getId());
        assertThat(contactMechType1).isEqualTo(contactMechType2);
        contactMechType2.setId(2L);
        assertThat(contactMechType1).isNotEqualTo(contactMechType2);
        contactMechType1.setId(null);
        assertThat(contactMechType1).isNotEqualTo(contactMechType2);
    }
}
