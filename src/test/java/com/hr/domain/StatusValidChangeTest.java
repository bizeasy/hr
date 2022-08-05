package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class StatusValidChangeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusValidChange.class);
        StatusValidChange statusValidChange1 = new StatusValidChange();
        statusValidChange1.setId(1L);
        StatusValidChange statusValidChange2 = new StatusValidChange();
        statusValidChange2.setId(statusValidChange1.getId());
        assertThat(statusValidChange1).isEqualTo(statusValidChange2);
        statusValidChange2.setId(2L);
        assertThat(statusValidChange1).isNotEqualTo(statusValidChange2);
        statusValidChange1.setId(null);
        assertThat(statusValidChange1).isNotEqualTo(statusValidChange2);
    }
}
