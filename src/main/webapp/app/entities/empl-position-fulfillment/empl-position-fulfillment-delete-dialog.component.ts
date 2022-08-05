import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmplPositionFulfillment } from 'app/shared/model/empl-position-fulfillment.model';
import { EmplPositionFulfillmentService } from './empl-position-fulfillment.service';

@Component({
  templateUrl: './empl-position-fulfillment-delete-dialog.component.html',
})
export class EmplPositionFulfillmentDeleteDialogComponent {
  emplPositionFulfillment?: IEmplPositionFulfillment;

  constructor(
    protected emplPositionFulfillmentService: EmplPositionFulfillmentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emplPositionFulfillmentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('emplPositionFulfillmentListModification');
      this.activeModal.close();
    });
  }
}
