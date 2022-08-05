import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkEffortInventoryAssign } from 'app/shared/model/work-effort-inventory-assign.model';
import { WorkEffortInventoryAssignService } from './work-effort-inventory-assign.service';

@Component({
  templateUrl: './work-effort-inventory-assign-delete-dialog.component.html',
})
export class WorkEffortInventoryAssignDeleteDialogComponent {
  workEffortInventoryAssign?: IWorkEffortInventoryAssign;

  constructor(
    protected workEffortInventoryAssignService: WorkEffortInventoryAssignService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workEffortInventoryAssignService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workEffortInventoryAssignListModification');
      this.activeModal.close();
    });
  }
}
