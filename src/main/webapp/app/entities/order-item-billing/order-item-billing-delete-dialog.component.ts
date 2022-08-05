import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderItemBilling } from 'app/shared/model/order-item-billing.model';
import { OrderItemBillingService } from './order-item-billing.service';

@Component({
  templateUrl: './order-item-billing-delete-dialog.component.html',
})
export class OrderItemBillingDeleteDialogComponent {
  orderItemBilling?: IOrderItemBilling;

  constructor(
    protected orderItemBillingService: OrderItemBillingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderItemBillingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('orderItemBillingListModification');
      this.activeModal.close();
    });
  }
}
