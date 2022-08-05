import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderStatus } from 'app/shared/model/order-status.model';
import { OrderStatusService } from './order-status.service';

@Component({
  templateUrl: './order-status-delete-dialog.component.html',
})
export class OrderStatusDeleteDialogComponent {
  orderStatus?: IOrderStatus;

  constructor(
    protected orderStatusService: OrderStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderStatusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('orderStatusListModification');
      this.activeModal.close();
    });
  }
}
