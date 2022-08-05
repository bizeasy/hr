import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJobInterviewType } from 'app/shared/model/job-interview-type.model';
import { JobInterviewTypeService } from './job-interview-type.service';
import { JobInterviewTypeDeleteDialogComponent } from './job-interview-type-delete-dialog.component';

@Component({
  selector: 'sys-job-interview-type',
  templateUrl: './job-interview-type.component.html',
})
export class JobInterviewTypeComponent implements OnInit, OnDestroy {
  jobInterviewTypes?: IJobInterviewType[];
  eventSubscriber?: Subscription;

  constructor(
    protected jobInterviewTypeService: JobInterviewTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.jobInterviewTypeService.query().subscribe((res: HttpResponse<IJobInterviewType[]>) => (this.jobInterviewTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInJobInterviewTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IJobInterviewType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInJobInterviewTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('jobInterviewTypeListModification', () => this.loadAll());
  }

  delete(jobInterviewType: IJobInterviewType): void {
    const modalRef = this.modalService.open(JobInterviewTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.jobInterviewType = jobInterviewType;
  }
}
