import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkEffortType } from 'app/shared/model/work-effort-type.model';
import { WorkEffortTypeService } from './work-effort-type.service';

@Component({
  templateUrl: './work-effort-type-delete-dialog.component.html',
})
export class WorkEffortTypeDeleteDialogComponent {
  workEffortType?: IWorkEffortType;

  constructor(
    protected workEffortTypeService: WorkEffortTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workEffortTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workEffortTypeListModification');
      this.activeModal.close();
    });
  }
}
