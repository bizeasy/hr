import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PaymentGatewayConfigComponent } from './payment-gateway-config.component';
import { PaymentGatewayConfigDetailComponent } from './payment-gateway-config-detail.component';
import { PaymentGatewayConfigUpdateComponent } from './payment-gateway-config-update.component';
import { PaymentGatewayConfigDeleteDialogComponent } from './payment-gateway-config-delete-dialog.component';
import { paymentGatewayConfigRoute } from './payment-gateway-config.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(paymentGatewayConfigRoute)],
  declarations: [
    PaymentGatewayConfigComponent,
    PaymentGatewayConfigDetailComponent,
    PaymentGatewayConfigUpdateComponent,
    PaymentGatewayConfigDeleteDialogComponent,
  ],
  entryComponents: [PaymentGatewayConfigDeleteDialogComponent],
})
export class HrPaymentGatewayConfigModule {}
