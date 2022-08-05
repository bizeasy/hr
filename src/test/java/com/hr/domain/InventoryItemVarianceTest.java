package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class InventoryItemVarianceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryItemVariance.class);
        InventoryItemVariance inventoryItemVariance1 = new InventoryItemVariance();
        inventoryItemVariance1.setId(1L);
        InventoryItemVariance inventoryItemVariance2 = new InventoryItemVariance();
        inventoryItemVariance2.setId(inventoryItemVariance1.getId());
        assertThat(inventoryItemVariance1).isEqualTo(inventoryItemVariance2);
        inventoryItemVariance2.setId(2L);
        assertThat(inventoryItemVariance1).isNotEqualTo(inventoryItemVariance2);
        inventoryItemVariance1.setId(null);
        assertThat(inventoryItemVariance1).isNotEqualTo(inventoryItemVariance2);
    }
}
