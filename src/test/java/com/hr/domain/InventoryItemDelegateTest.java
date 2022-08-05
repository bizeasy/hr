package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class InventoryItemDelegateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryItemDelegate.class);
        InventoryItemDelegate inventoryItemDelegate1 = new InventoryItemDelegate();
        inventoryItemDelegate1.setId(1L);
        InventoryItemDelegate inventoryItemDelegate2 = new InventoryItemDelegate();
        inventoryItemDelegate2.setId(inventoryItemDelegate1.getId());
        assertThat(inventoryItemDelegate1).isEqualTo(inventoryItemDelegate2);
        inventoryItemDelegate2.setId(2L);
        assertThat(inventoryItemDelegate1).isNotEqualTo(inventoryItemDelegate2);
        inventoryItemDelegate1.setId(null);
        assertThat(inventoryItemDelegate1).isNotEqualTo(inventoryItemDelegate2);
    }
}
