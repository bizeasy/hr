package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class KeywordTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeywordType.class);
        KeywordType keywordType1 = new KeywordType();
        keywordType1.setId(1L);
        KeywordType keywordType2 = new KeywordType();
        keywordType2.setId(keywordType1.getId());
        assertThat(keywordType1).isEqualTo(keywordType2);
        keywordType2.setId(2L);
        assertThat(keywordType1).isNotEqualTo(keywordType2);
        keywordType1.setId(null);
        assertThat(keywordType1).isNotEqualTo(keywordType2);
    }
}
