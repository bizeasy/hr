package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class PerfRatingTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfRatingType.class);
        PerfRatingType perfRatingType1 = new PerfRatingType();
        perfRatingType1.setId(1L);
        PerfRatingType perfRatingType2 = new PerfRatingType();
        perfRatingType2.setId(perfRatingType1.getId());
        assertThat(perfRatingType1).isEqualTo(perfRatingType2);
        perfRatingType2.setId(2L);
        assertThat(perfRatingType1).isNotEqualTo(perfRatingType2);
        perfRatingType1.setId(null);
        assertThat(perfRatingType1).isNotEqualTo(perfRatingType2);
    }
}
