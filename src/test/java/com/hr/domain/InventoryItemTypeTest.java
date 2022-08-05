package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class InventoryItemTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryItemType.class);
        InventoryItemType inventoryItemType1 = new InventoryItemType();
        inventoryItemType1.setId(1L);
        InventoryItemType inventoryItemType2 = new InventoryItemType();
        inventoryItemType2.setId(inventoryItemType1.getId());
        assertThat(inventoryItemType1).isEqualTo(inventoryItemType2);
        inventoryItemType2.setId(2L);
        assertThat(inventoryItemType1).isNotEqualTo(inventoryItemType2);
        inventoryItemType1.setId(null);
        assertThat(inventoryItemType1).isNotEqualTo(inventoryItemType2);
    }
}
