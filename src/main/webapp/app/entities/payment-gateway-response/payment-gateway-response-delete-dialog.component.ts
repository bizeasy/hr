import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentGatewayResponse } from 'app/shared/model/payment-gateway-response.model';
import { PaymentGatewayResponseService } from './payment-gateway-response.service';

@Component({
  templateUrl: './payment-gateway-response-delete-dialog.component.html',
})
export class PaymentGatewayResponseDeleteDialogComponent {
  paymentGatewayResponse?: IPaymentGatewayResponse;

  constructor(
    protected paymentGatewayResponseService: PaymentGatewayResponseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentGatewayResponseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paymentGatewayResponseListModification');
      this.activeModal.close();
    });
  }
}
