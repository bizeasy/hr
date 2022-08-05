package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class SupplierProductTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierProduct.class);
        SupplierProduct supplierProduct1 = new SupplierProduct();
        supplierProduct1.setId(1L);
        SupplierProduct supplierProduct2 = new SupplierProduct();
        supplierProduct2.setId(supplierProduct1.getId());
        assertThat(supplierProduct1).isEqualTo(supplierProduct2);
        supplierProduct2.setId(2L);
        assertThat(supplierProduct1).isNotEqualTo(supplierProduct2);
        supplierProduct1.setId(null);
        assertThat(supplierProduct1).isNotEqualTo(supplierProduct2);
    }
}
