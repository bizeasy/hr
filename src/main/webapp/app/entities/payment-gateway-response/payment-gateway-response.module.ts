import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PaymentGatewayResponseComponent } from './payment-gateway-response.component';
import { PaymentGatewayResponseDetailComponent } from './payment-gateway-response-detail.component';
import { PaymentGatewayResponseUpdateComponent } from './payment-gateway-response-update.component';
import { PaymentGatewayResponseDeleteDialogComponent } from './payment-gateway-response-delete-dialog.component';
import { paymentGatewayResponseRoute } from './payment-gateway-response.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(paymentGatewayResponseRoute)],
  declarations: [
    PaymentGatewayResponseComponent,
    PaymentGatewayResponseDetailComponent,
    PaymentGatewayResponseUpdateComponent,
    PaymentGatewayResponseDeleteDialogComponent,
  ],
  entryComponents: [PaymentGatewayResponseDeleteDialogComponent],
})
export class HrPaymentGatewayResponseModule {}
