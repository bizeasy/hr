import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkEffortAssoc } from 'app/shared/model/work-effort-assoc.model';
import { WorkEffortAssocService } from './work-effort-assoc.service';

@Component({
  templateUrl: './work-effort-assoc-delete-dialog.component.html',
})
export class WorkEffortAssocDeleteDialogComponent {
  workEffortAssoc?: IWorkEffortAssoc;

  constructor(
    protected workEffortAssocService: WorkEffortAssocService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workEffortAssocService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workEffortAssocListModification');
      this.activeModal.close();
    });
  }
}
