import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentMethodType } from 'app/shared/model/payment-method-type.model';
import { PaymentMethodTypeService } from './payment-method-type.service';

@Component({
  templateUrl: './payment-method-type-delete-dialog.component.html',
})
export class PaymentMethodTypeDeleteDialogComponent {
  paymentMethodType?: IPaymentMethodType;

  constructor(
    protected paymentMethodTypeService: PaymentMethodTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentMethodTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paymentMethodTypeListModification');
      this.activeModal.close();
    });
  }
}
