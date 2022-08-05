import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPerfReviewItemType } from 'app/shared/model/perf-review-item-type.model';
import { PerfReviewItemTypeService } from './perf-review-item-type.service';
import { PerfReviewItemTypeDeleteDialogComponent } from './perf-review-item-type-delete-dialog.component';

@Component({
  selector: 'sys-perf-review-item-type',
  templateUrl: './perf-review-item-type.component.html',
})
export class PerfReviewItemTypeComponent implements OnInit, OnDestroy {
  perfReviewItemTypes?: IPerfReviewItemType[];
  eventSubscriber?: Subscription;

  constructor(
    protected perfReviewItemTypeService: PerfReviewItemTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.perfReviewItemTypeService
      .query()
      .subscribe((res: HttpResponse<IPerfReviewItemType[]>) => (this.perfReviewItemTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPerfReviewItemTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPerfReviewItemType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPerfReviewItemTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('perfReviewItemTypeListModification', () => this.loadAll());
  }

  delete(perfReviewItemType: IPerfReviewItemType): void {
    const modalRef = this.modalService.open(PerfReviewItemTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.perfReviewItemType = perfReviewItemType;
  }
}
