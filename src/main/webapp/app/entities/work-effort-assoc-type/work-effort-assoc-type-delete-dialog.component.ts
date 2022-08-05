import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkEffortAssocType } from 'app/shared/model/work-effort-assoc-type.model';
import { WorkEffortAssocTypeService } from './work-effort-assoc-type.service';

@Component({
  templateUrl: './work-effort-assoc-type-delete-dialog.component.html',
})
export class WorkEffortAssocTypeDeleteDialogComponent {
  workEffortAssocType?: IWorkEffortAssocType;

  constructor(
    protected workEffortAssocTypeService: WorkEffortAssocTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workEffortAssocTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workEffortAssocTypeListModification');
      this.activeModal.close();
    });
  }
}
