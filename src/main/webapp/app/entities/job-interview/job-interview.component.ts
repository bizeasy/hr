import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJobInterview } from 'app/shared/model/job-interview.model';
import { JobInterviewService } from './job-interview.service';
import { JobInterviewDeleteDialogComponent } from './job-interview-delete-dialog.component';

@Component({
  selector: 'sys-job-interview',
  templateUrl: './job-interview.component.html',
})
export class JobInterviewComponent implements OnInit, OnDestroy {
  jobInterviews?: IJobInterview[];
  eventSubscriber?: Subscription;

  constructor(
    protected jobInterviewService: JobInterviewService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.jobInterviewService.query().subscribe((res: HttpResponse<IJobInterview[]>) => (this.jobInterviews = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInJobInterviews();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IJobInterview): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInJobInterviews(): void {
    this.eventSubscriber = this.eventManager.subscribe('jobInterviewListModification', () => this.loadAll());
  }

  delete(jobInterview: IJobInterview): void {
    const modalRef = this.modalService.open(JobInterviewDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.jobInterview = jobInterview;
  }
}
