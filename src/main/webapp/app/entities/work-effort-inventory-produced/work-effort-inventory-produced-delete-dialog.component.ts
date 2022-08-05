import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkEffortInventoryProduced } from 'app/shared/model/work-effort-inventory-produced.model';
import { WorkEffortInventoryProducedService } from './work-effort-inventory-produced.service';

@Component({
  templateUrl: './work-effort-inventory-produced-delete-dialog.component.html',
})
export class WorkEffortInventoryProducedDeleteDialogComponent {
  workEffortInventoryProduced?: IWorkEffortInventoryProduced;

  constructor(
    protected workEffortInventoryProducedService: WorkEffortInventoryProducedService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workEffortInventoryProducedService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workEffortInventoryProducedListModification');
      this.activeModal.close();
    });
  }
}
