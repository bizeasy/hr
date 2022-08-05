import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { OrderTypeComponent } from './order-type.component';
import { OrderTypeDetailComponent } from './order-type-detail.component';
import { OrderTypeUpdateComponent } from './order-type-update.component';
import { OrderTypeDeleteDialogComponent } from './order-type-delete-dialog.component';
import { orderTypeRoute } from './order-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(orderTypeRoute)],
  declarations: [OrderTypeComponent, OrderTypeDetailComponent, OrderTypeUpdateComponent, OrderTypeDeleteDialogComponent],
  entryComponents: [OrderTypeDeleteDialogComponent],
})
export class HrOrderTypeModule {}
