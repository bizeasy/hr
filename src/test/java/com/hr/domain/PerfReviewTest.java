package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PerfReviewTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfReview.class);
        PerfReview perfReview1 = new PerfReview();
        perfReview1.setId(1L);
        PerfReview perfReview2 = new PerfReview();
        perfReview2.setId(perfReview1.getId());
        assertThat(perfReview1).isEqualTo(perfReview2);
        perfReview2.setId(2L);
        assertThat(perfReview1).isNotEqualTo(perfReview2);
        perfReview1.setId(null);
        assertThat(perfReview1).isNotEqualTo(perfReview2);
    }
}
