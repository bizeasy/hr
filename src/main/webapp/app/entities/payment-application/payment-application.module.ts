import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PaymentApplicationComponent } from './payment-application.component';
import { PaymentApplicationDetailComponent } from './payment-application-detail.component';
import { PaymentApplicationUpdateComponent } from './payment-application-update.component';
import { PaymentApplicationDeleteDialogComponent } from './payment-application-delete-dialog.component';
import { paymentApplicationRoute } from './payment-application.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(paymentApplicationRoute)],
  declarations: [
    PaymentApplicationComponent,
    PaymentApplicationDetailComponent,
    PaymentApplicationUpdateComponent,
    PaymentApplicationDeleteDialogComponent,
  ],
  entryComponents: [PaymentApplicationDeleteDialogComponent],
})
export class HrPaymentApplicationModule {}
