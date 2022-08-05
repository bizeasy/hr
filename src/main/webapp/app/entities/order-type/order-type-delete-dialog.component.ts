import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderType } from 'app/shared/model/order-type.model';
import { OrderTypeService } from './order-type.service';

@Component({
  templateUrl: './order-type-delete-dialog.component.html',
})
export class OrderTypeDeleteDialogComponent {
  orderType?: IOrderType;

  constructor(protected orderTypeService: OrderTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('orderTypeListModification');
      this.activeModal.close();
    });
  }
}
