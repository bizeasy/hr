package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PerfReviewItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfReviewItem.class);
        PerfReviewItem perfReviewItem1 = new PerfReviewItem();
        perfReviewItem1.setId(1L);
        PerfReviewItem perfReviewItem2 = new PerfReviewItem();
        perfReviewItem2.setId(perfReviewItem1.getId());
        assertThat(perfReviewItem1).isEqualTo(perfReviewItem2);
        perfReviewItem2.setId(2L);
        assertThat(perfReviewItem1).isNotEqualTo(perfReviewItem2);
        perfReviewItem1.setId(null);
        assertThat(perfReviewItem1).isNotEqualTo(perfReviewItem2);
    }
}
