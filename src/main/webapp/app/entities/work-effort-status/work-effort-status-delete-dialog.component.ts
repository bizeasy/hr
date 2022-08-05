import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkEffortStatus } from 'app/shared/model/work-effort-status.model';
import { WorkEffortStatusService } from './work-effort-status.service';

@Component({
  templateUrl: './work-effort-status-delete-dialog.component.html',
})
export class WorkEffortStatusDeleteDialogComponent {
  workEffortStatus?: IWorkEffortStatus;

  constructor(
    protected workEffortStatusService: WorkEffortStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workEffortStatusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workEffortStatusListModification');
      this.activeModal.close();
    });
  }
}
