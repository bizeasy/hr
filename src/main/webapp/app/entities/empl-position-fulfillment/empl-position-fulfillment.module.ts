import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { EmplPositionFulfillmentComponent } from './empl-position-fulfillment.component';
import { EmplPositionFulfillmentDetailComponent } from './empl-position-fulfillment-detail.component';
import { EmplPositionFulfillmentUpdateComponent } from './empl-position-fulfillment-update.component';
import { EmplPositionFulfillmentDeleteDialogComponent } from './empl-position-fulfillment-delete-dialog.component';
import { emplPositionFulfillmentRoute } from './empl-position-fulfillment.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(emplPositionFulfillmentRoute)],
  declarations: [
    EmplPositionFulfillmentComponent,
    EmplPositionFulfillmentDetailComponent,
    EmplPositionFulfillmentUpdateComponent,
    EmplPositionFulfillmentDeleteDialogComponent,
  ],
  entryComponents: [EmplPositionFulfillmentDeleteDialogComponent],
})
export class HrEmplPositionFulfillmentModule {}
