import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJobRequisition } from 'app/shared/model/job-requisition.model';
import { JobRequisitionService } from './job-requisition.service';
import { JobRequisitionDeleteDialogComponent } from './job-requisition-delete-dialog.component';

@Component({
  selector: 'sys-job-requisition',
  templateUrl: './job-requisition.component.html',
})
export class JobRequisitionComponent implements OnInit, OnDestroy {
  jobRequisitions?: IJobRequisition[];
  eventSubscriber?: Subscription;

  constructor(
    protected jobRequisitionService: JobRequisitionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.jobRequisitionService.query().subscribe((res: HttpResponse<IJobRequisition[]>) => (this.jobRequisitions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInJobRequisitions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IJobRequisition): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInJobRequisitions(): void {
    this.eventSubscriber = this.eventManager.subscribe('jobRequisitionListModification', () => this.loadAll());
  }

  delete(jobRequisition: IJobRequisition): void {
    const modalRef = this.modalService.open(JobRequisitionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.jobRequisition = jobRequisition;
  }
}
