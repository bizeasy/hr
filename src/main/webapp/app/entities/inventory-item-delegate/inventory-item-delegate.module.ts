import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { InventoryItemDelegateComponent } from './inventory-item-delegate.component';
import { InventoryItemDelegateDetailComponent } from './inventory-item-delegate-detail.component';
import { InventoryItemDelegateUpdateComponent } from './inventory-item-delegate-update.component';
import { InventoryItemDelegateDeleteDialogComponent } from './inventory-item-delegate-delete-dialog.component';
import { inventoryItemDelegateRoute } from './inventory-item-delegate.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(inventoryItemDelegateRoute)],
  declarations: [
    InventoryItemDelegateComponent,
    InventoryItemDelegateDetailComponent,
    InventoryItemDelegateUpdateComponent,
    InventoryItemDelegateDeleteDialogComponent,
  ],
  entryComponents: [InventoryItemDelegateDeleteDialogComponent],
})
export class HrInventoryItemDelegateModule {}
