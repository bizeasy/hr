import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobInterviewType } from 'app/shared/model/job-interview-type.model';
import { JobInterviewTypeService } from './job-interview-type.service';

@Component({
  templateUrl: './job-interview-type-delete-dialog.component.html',
})
export class JobInterviewTypeDeleteDialogComponent {
  jobInterviewType?: IJobInterviewType;

  constructor(
    protected jobInterviewTypeService: JobInterviewTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jobInterviewTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jobInterviewTypeListModification');
      this.activeModal.close();
    });
  }
}
