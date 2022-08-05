import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { InventoryItemTypeComponent } from './inventory-item-type.component';
import { InventoryItemTypeDetailComponent } from './inventory-item-type-detail.component';
import { InventoryItemTypeUpdateComponent } from './inventory-item-type-update.component';
import { InventoryItemTypeDeleteDialogComponent } from './inventory-item-type-delete-dialog.component';
import { inventoryItemTypeRoute } from './inventory-item-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(inventoryItemTypeRoute)],
  declarations: [
    InventoryItemTypeComponent,
    InventoryItemTypeDetailComponent,
    InventoryItemTypeUpdateComponent,
    InventoryItemTypeDeleteDialogComponent,
  ],
  entryComponents: [InventoryItemTypeDeleteDialogComponent],
})
export class HrInventoryItemTypeModule {}
