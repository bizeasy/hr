import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PaymentMethodTypeComponent } from './payment-method-type.component';
import { PaymentMethodTypeDetailComponent } from './payment-method-type-detail.component';
import { PaymentMethodTypeUpdateComponent } from './payment-method-type-update.component';
import { PaymentMethodTypeDeleteDialogComponent } from './payment-method-type-delete-dialog.component';
import { paymentMethodTypeRoute } from './payment-method-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(paymentMethodTypeRoute)],
  declarations: [
    PaymentMethodTypeComponent,
    PaymentMethodTypeDetailComponent,
    PaymentMethodTypeUpdateComponent,
    PaymentMethodTypeDeleteDialogComponent,
  ],
  entryComponents: [PaymentMethodTypeDeleteDialogComponent],
})
export class HrPaymentMethodTypeModule {}
