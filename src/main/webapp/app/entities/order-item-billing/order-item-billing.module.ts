import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { OrderItemBillingComponent } from './order-item-billing.component';
import { OrderItemBillingDetailComponent } from './order-item-billing-detail.component';
import { OrderItemBillingUpdateComponent } from './order-item-billing-update.component';
import { OrderItemBillingDeleteDialogComponent } from './order-item-billing-delete-dialog.component';
import { orderItemBillingRoute } from './order-item-billing.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(orderItemBillingRoute)],
  declarations: [
    OrderItemBillingComponent,
    OrderItemBillingDetailComponent,
    OrderItemBillingUpdateComponent,
    OrderItemBillingDeleteDialogComponent,
  ],
  entryComponents: [OrderItemBillingDeleteDialogComponent],
})
export class HrOrderItemBillingModule {}
