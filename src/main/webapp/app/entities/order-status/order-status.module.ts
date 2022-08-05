import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { OrderStatusComponent } from './order-status.component';
import { OrderStatusDetailComponent } from './order-status-detail.component';
import { OrderStatusUpdateComponent } from './order-status-update.component';
import { OrderStatusDeleteDialogComponent } from './order-status-delete-dialog.component';
import { orderStatusRoute } from './order-status.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(orderStatusRoute)],
  declarations: [OrderStatusComponent, OrderStatusDetailComponent, OrderStatusUpdateComponent, OrderStatusDeleteDialogComponent],
  entryComponents: [OrderStatusDeleteDialogComponent],
})
export class HrOrderStatusModule {}
