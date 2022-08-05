package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class SalesChannelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesChannel.class);
        SalesChannel salesChannel1 = new SalesChannel();
        salesChannel1.setId(1L);
        SalesChannel salesChannel2 = new SalesChannel();
        salesChannel2.setId(salesChannel1.getId());
        assertThat(salesChannel1).isEqualTo(salesChannel2);
        salesChannel2.setId(2L);
        assertThat(salesChannel1).isNotEqualTo(salesChannel2);
        salesChannel1.setId(null);
        assertThat(salesChannel1).isNotEqualTo(salesChannel2);
    }
}
