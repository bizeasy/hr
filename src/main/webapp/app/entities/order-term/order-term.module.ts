import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { OrderTermComponent } from './order-term.component';
import { OrderTermDetailComponent } from './order-term-detail.component';
import { OrderTermUpdateComponent } from './order-term-update.component';
import { OrderTermDeleteDialogComponent } from './order-term-delete-dialog.component';
import { orderTermRoute } from './order-term.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(orderTermRoute)],
  declarations: [OrderTermComponent, OrderTermDetailComponent, OrderTermUpdateComponent, OrderTermDeleteDialogComponent],
  entryComponents: [OrderTermDeleteDialogComponent],
})
export class HrOrderTermModule {}
