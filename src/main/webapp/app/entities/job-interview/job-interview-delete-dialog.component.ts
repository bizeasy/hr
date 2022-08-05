import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobInterview } from 'app/shared/model/job-interview.model';
import { JobInterviewService } from './job-interview.service';

@Component({
  templateUrl: './job-interview-delete-dialog.component.html',
})
export class JobInterviewDeleteDialogComponent {
  jobInterview?: IJobInterview;

  constructor(
    protected jobInterviewService: JobInterviewService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jobInterviewService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jobInterviewListModification');
      this.activeModal.close();
    });
  }
}
