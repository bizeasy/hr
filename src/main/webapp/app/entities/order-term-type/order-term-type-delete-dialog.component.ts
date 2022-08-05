import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderTermType } from 'app/shared/model/order-term-type.model';
import { OrderTermTypeService } from './order-term-type.service';

@Component({
  templateUrl: './order-term-type-delete-dialog.component.html',
})
export class OrderTermTypeDeleteDialogComponent {
  orderTermType?: IOrderTermType;

  constructor(
    protected orderTermTypeService: OrderTermTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderTermTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('orderTermTypeListModification');
      this.activeModal.close();
    });
  }
}
