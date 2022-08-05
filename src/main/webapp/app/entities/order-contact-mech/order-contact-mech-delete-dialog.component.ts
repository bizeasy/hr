import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderContactMech } from 'app/shared/model/order-contact-mech.model';
import { OrderContactMechService } from './order-contact-mech.service';

@Component({
  templateUrl: './order-contact-mech-delete-dialog.component.html',
})
export class OrderContactMechDeleteDialogComponent {
  orderContactMech?: IOrderContactMech;

  constructor(
    protected orderContactMechService: OrderContactMechService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderContactMechService.delete(id).subscribe(() => {
      this.eventManager.broadcast('orderContactMechListModification');
      this.activeModal.close();
    });
  }
}
