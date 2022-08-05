package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class UomTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uom.class);
        Uom uom1 = new Uom();
        uom1.setId(1L);
        Uom uom2 = new Uom();
        uom2.setId(uom1.getId());
        assertThat(uom1).isEqualTo(uom2);
        uom2.setId(2L);
        assertThat(uom1).isNotEqualTo(uom2);
        uom1.setId(null);
        assertThat(uom1).isNotEqualTo(uom2);
    }
}
