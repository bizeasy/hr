package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class ContactMechTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactMech.class);
        ContactMech contactMech1 = new ContactMech();
        contactMech1.setId(1L);
        ContactMech contactMech2 = new ContactMech();
        contactMech2.setId(contactMech1.getId());
        assertThat(contactMech1).isEqualTo(contactMech2);
        contactMech2.setId(2L);
        assertThat(contactMech1).isNotEqualTo(contactMech2);
        contactMech1.setId(null);
        assertThat(contactMech1).isNotEqualTo(contactMech2);
    }
}
