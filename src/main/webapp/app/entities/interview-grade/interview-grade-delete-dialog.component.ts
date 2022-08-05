import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInterviewGrade } from 'app/shared/model/interview-grade.model';
import { InterviewGradeService } from './interview-grade.service';

@Component({
  templateUrl: './interview-grade-delete-dialog.component.html',
})
export class InterviewGradeDeleteDialogComponent {
  interviewGrade?: IInterviewGrade;

  constructor(
    protected interviewGradeService: InterviewGradeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.interviewGradeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('interviewGradeListModification');
      this.activeModal.close();
    });
  }
}
