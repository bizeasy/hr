package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class CatalogueCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatalogueCategory.class);
        CatalogueCategory catalogueCategory1 = new CatalogueCategory();
        catalogueCategory1.setId(1L);
        CatalogueCategory catalogueCategory2 = new CatalogueCategory();
        catalogueCategory2.setId(catalogueCategory1.getId());
        assertThat(catalogueCategory1).isEqualTo(catalogueCategory2);
        catalogueCategory2.setId(2L);
        assertThat(catalogueCategory1).isNotEqualTo(catalogueCategory2);
        catalogueCategory1.setId(null);
        assertThat(catalogueCategory1).isNotEqualTo(catalogueCategory2);
    }
}
