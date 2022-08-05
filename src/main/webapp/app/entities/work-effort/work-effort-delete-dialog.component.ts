import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkEffort } from 'app/shared/model/work-effort.model';
import { WorkEffortService } from './work-effort.service';

@Component({
  templateUrl: './work-effort-delete-dialog.component.html',
})
export class WorkEffortDeleteDialogComponent {
  workEffort?: IWorkEffort;

  constructor(
    protected workEffortService: WorkEffortService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workEffortService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workEffortListModification');
      this.activeModal.close();
    });
  }
}
