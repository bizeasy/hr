package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PerfReviewItemTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfReviewItemType.class);
        PerfReviewItemType perfReviewItemType1 = new PerfReviewItemType();
        perfReviewItemType1.setId(1L);
        PerfReviewItemType perfReviewItemType2 = new PerfReviewItemType();
        perfReviewItemType2.setId(perfReviewItemType1.getId());
        assertThat(perfReviewItemType1).isEqualTo(perfReviewItemType2);
        perfReviewItemType2.setId(2L);
        assertThat(perfReviewItemType1).isNotEqualTo(perfReviewItemType2);
        perfReviewItemType1.setId(null);
        assertThat(perfReviewItemType1).isNotEqualTo(perfReviewItemType2);
    }
}
