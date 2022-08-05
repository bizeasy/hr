package com.hr.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hr.web.rest.TestUtil;

public class InventoryTransferTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryTransfer.class);
        InventoryTransfer inventoryTransfer1 = new InventoryTransfer();
        inventoryTransfer1.setId(1L);
        InventoryTransfer inventoryTransfer2 = new InventoryTransfer();
        inventoryTransfer2.setId(inventoryTransfer1.getId());
        assertThat(inventoryTransfer1).isEqualTo(inventoryTransfer2);
        inventoryTransfer2.setId(2L);
        assertThat(inventoryTransfer1).isNotEqualTo(inventoryTransfer2);
        inventoryTransfer1.setId(null);
        assertThat(inventoryTransfer1).isNotEqualTo(inventoryTransfer2);
    }
}
