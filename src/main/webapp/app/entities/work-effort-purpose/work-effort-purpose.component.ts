import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkEffortPurpose } from 'app/shared/model/work-effort-purpose.model';
import { WorkEffortPurposeService } from './work-effort-purpose.service';
import { WorkEffortPurposeDeleteDialogComponent } from './work-effort-purpose-delete-dialog.component';

@Component({
  selector: 'sys-work-effort-purpose',
  templateUrl: './work-effort-purpose.component.html',
})
export class WorkEffortPurposeComponent implements OnInit, OnDestroy {
  workEffortPurposes?: IWorkEffortPurpose[];
  eventSubscriber?: Subscription;

  constructor(
    protected workEffortPurposeService: WorkEffortPurposeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.workEffortPurposeService
      .query()
      .subscribe((res: HttpResponse<IWorkEffortPurpose[]>) => (this.workEffortPurposes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWorkEffortPurposes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWorkEffortPurpose): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWorkEffortPurposes(): void {
    this.eventSubscriber = this.eventManager.subscribe('workEffortPurposeListModification', () => this.loadAll());
  }

  delete(workEffortPurpose: IWorkEffortPurpose): void {
    const modalRef = this.modalService.open(WorkEffortPurposeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.workEffortPurpose = workEffortPurpose;
  }
}
