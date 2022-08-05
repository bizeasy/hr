import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderItemType } from 'app/shared/model/order-item-type.model';
import { OrderItemTypeService } from './order-item-type.service';

@Component({
  templateUrl: './order-item-type-delete-dialog.component.html',
})
export class OrderItemTypeDeleteDialogComponent {
  orderItemType?: IOrderItemType;

  constructor(
    protected orderItemTypeService: OrderItemTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderItemTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('orderItemTypeListModification');
      this.activeModal.close();
    });
  }
}
