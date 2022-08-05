import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { InventoryItemVarianceComponent } from './inventory-item-variance.component';
import { InventoryItemVarianceDetailComponent } from './inventory-item-variance-detail.component';
import { InventoryItemVarianceUpdateComponent } from './inventory-item-variance-update.component';
import { InventoryItemVarianceDeleteDialogComponent } from './inventory-item-variance-delete-dialog.component';
import { inventoryItemVarianceRoute } from './inventory-item-variance.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(inventoryItemVarianceRoute)],
  declarations: [
    InventoryItemVarianceComponent,
    InventoryItemVarianceDetailComponent,
    InventoryItemVarianceUpdateComponent,
    InventoryItemVarianceDeleteDialogComponent,
  ],
  entryComponents: [InventoryItemVarianceDeleteDialogComponent],
})
export class HrInventoryItemVarianceModule {}
