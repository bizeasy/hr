import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobRequisition } from 'app/shared/model/job-requisition.model';
import { JobRequisitionService } from './job-requisition.service';

@Component({
  templateUrl: './job-requisition-delete-dialog.component.html',
})
export class JobRequisitionDeleteDialogComponent {
  jobRequisition?: IJobRequisition;

  constructor(
    protected jobRequisitionService: JobRequisitionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jobRequisitionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jobRequisitionListModification');
      this.activeModal.close();
    });
  }
}
