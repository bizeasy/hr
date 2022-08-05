import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentApplication } from 'app/shared/model/payment-application.model';
import { PaymentApplicationService } from './payment-application.service';

@Component({
  templateUrl: './payment-application-delete-dialog.component.html',
})
export class PaymentApplicationDeleteDialogComponent {
  paymentApplication?: IPaymentApplication;

  constructor(
    protected paymentApplicationService: PaymentApplicationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentApplicationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paymentApplicationListModification');
      this.activeModal.close();
    });
  }
}
