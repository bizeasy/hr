import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentGatewayConfig } from 'app/shared/model/payment-gateway-config.model';
import { PaymentGatewayConfigService } from './payment-gateway-config.service';

@Component({
  templateUrl: './payment-gateway-config-delete-dialog.component.html',
})
export class PaymentGatewayConfigDeleteDialogComponent {
  paymentGatewayConfig?: IPaymentGatewayConfig;

  constructor(
    protected paymentGatewayConfigService: PaymentGatewayConfigService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentGatewayConfigService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paymentGatewayConfigListModification');
      this.activeModal.close();
    });
  }
}
