import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkEffortPurpose } from 'app/shared/model/work-effort-purpose.model';
import { WorkEffortPurposeService } from './work-effort-purpose.service';

@Component({
  templateUrl: './work-effort-purpose-delete-dialog.component.html',
})
export class WorkEffortPurposeDeleteDialogComponent {
  workEffortPurpose?: IWorkEffortPurpose;

  constructor(
    protected workEffortPurposeService: WorkEffortPurposeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workEffortPurposeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workEffortPurposeListModification');
      this.activeModal.close();
    });
  }
}
