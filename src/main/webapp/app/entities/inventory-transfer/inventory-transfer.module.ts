import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { InventoryTransferComponent } from './inventory-transfer.component';
import { InventoryTransferDetailComponent } from './inventory-transfer-detail.component';
import { InventoryTransferUpdateComponent } from './inventory-transfer-update.component';
import { InventoryTransferDeleteDialogComponent } from './inventory-transfer-delete-dialog.component';
import { inventoryTransferRoute } from './inventory-transfer.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(inventoryTransferRoute)],
  declarations: [
    InventoryTransferComponent,
    InventoryTransferDetailComponent,
    InventoryTransferUpdateComponent,
    InventoryTransferDeleteDialogComponent,
  ],
  entryComponents: [InventoryTransferDeleteDialogComponent],
})
export class HrInventoryTransferModule {}
