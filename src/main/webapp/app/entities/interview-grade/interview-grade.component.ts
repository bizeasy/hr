import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInterviewGrade } from 'app/shared/model/interview-grade.model';
import { InterviewGradeService } from './interview-grade.service';
import { InterviewGradeDeleteDialogComponent } from './interview-grade-delete-dialog.component';

@Component({
  selector: 'sys-interview-grade',
  templateUrl: './interview-grade.component.html',
})
export class InterviewGradeComponent implements OnInit, OnDestroy {
  interviewGrades?: IInterviewGrade[];
  eventSubscriber?: Subscription;

  constructor(
    protected interviewGradeService: InterviewGradeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.interviewGradeService.query().subscribe((res: HttpResponse<IInterviewGrade[]>) => (this.interviewGrades = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInterviewGrades();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInterviewGrade): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInterviewGrades(): void {
    this.eventSubscriber = this.eventManager.subscribe('interviewGradeListModification', () => this.loadAll());
  }

  delete(interviewGrade: IInterviewGrade): void {
    const modalRef = this.modalService.open(InterviewGradeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.interviewGrade = interviewGrade;
  }
}
