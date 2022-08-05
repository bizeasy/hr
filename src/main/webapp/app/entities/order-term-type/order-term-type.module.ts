import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { OrderTermTypeComponent } from './order-term-type.component';
import { OrderTermTypeDetailComponent } from './order-term-type-detail.component';
import { OrderTermTypeUpdateComponent } from './order-term-type-update.component';
import { OrderTermTypeDeleteDialogComponent } from './order-term-type-delete-dialog.component';
import { orderTermTypeRoute } from './order-term-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(orderTermTypeRoute)],
  declarations: [OrderTermTypeComponent, OrderTermTypeDetailComponent, OrderTermTypeUpdateComponent, OrderTermTypeDeleteDialogComponent],
  entryComponents: [OrderTermTypeDeleteDialogComponent],
})
export class HrOrderTermTypeModule {}
