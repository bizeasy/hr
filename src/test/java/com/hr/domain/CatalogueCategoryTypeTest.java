package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class CatalogueCategoryTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatalogueCategoryType.class);
        CatalogueCategoryType catalogueCategoryType1 = new CatalogueCategoryType();
        catalogueCategoryType1.setId(1L);
        CatalogueCategoryType catalogueCategoryType2 = new CatalogueCategoryType();
        catalogueCategoryType2.setId(catalogueCategoryType1.getId());
        assertThat(catalogueCategoryType1).isEqualTo(catalogueCategoryType2);
        catalogueCategoryType2.setId(2L);
        assertThat(catalogueCategoryType1).isNotEqualTo(catalogueCategoryType2);
        catalogueCategoryType1.setId(null);
        assertThat(catalogueCategoryType1).isNotEqualTo(catalogueCategoryType2);
    }
}
