import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { OrderContactMechComponent } from './order-contact-mech.component';
import { OrderContactMechDetailComponent } from './order-contact-mech-detail.component';
import { OrderContactMechUpdateComponent } from './order-contact-mech-update.component';
import { OrderContactMechDeleteDialogComponent } from './order-contact-mech-delete-dialog.component';
import { orderContactMechRoute } from './order-contact-mech.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(orderContactMechRoute)],
  declarations: [
    OrderContactMechComponent,
    OrderContactMechDetailComponent,
    OrderContactMechUpdateComponent,
    OrderContactMechDeleteDialogComponent,
  ],
  entryComponents: [OrderContactMechDeleteDialogComponent],
})
export class HrOrderContactMechModule {}
