import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderTerm } from 'app/shared/model/order-term.model';
import { OrderTermService } from './order-term.service';

@Component({
  templateUrl: './order-term-delete-dialog.component.html',
})
export class OrderTermDeleteDialogComponent {
  orderTerm?: IOrderTerm;

  constructor(protected orderTermService: OrderTermService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderTermService.delete(id).subscribe(() => {
      this.eventManager.broadcast('orderTermListModification');
      this.activeModal.close();
    });
  }
}
