import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { OrderItemTypeComponent } from './order-item-type.component';
import { OrderItemTypeDetailComponent } from './order-item-type-detail.component';
import { OrderItemTypeUpdateComponent } from './order-item-type-update.component';
import { OrderItemTypeDeleteDialogComponent } from './order-item-type-delete-dialog.component';
import { orderItemTypeRoute } from './order-item-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(orderItemTypeRoute)],
  declarations: [OrderItemTypeComponent, OrderItemTypeDetailComponent, OrderItemTypeUpdateComponent, OrderItemTypeDeleteDialogComponent],
  entryComponents: [OrderItemTypeDeleteDialogComponent],
})
export class HrOrderItemTypeModule {}
