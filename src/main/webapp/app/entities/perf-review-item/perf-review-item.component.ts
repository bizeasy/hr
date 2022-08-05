import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPerfReviewItem } from 'app/shared/model/perf-review-item.model';
import { PerfReviewItemService } from './perf-review-item.service';
import { PerfReviewItemDeleteDialogComponent } from './perf-review-item-delete-dialog.component';

@Component({
  selector: 'sys-perf-review-item',
  templateUrl: './perf-review-item.component.html',
})
export class PerfReviewItemComponent implements OnInit, OnDestroy {
  perfReviewItems?: IPerfReviewItem[];
  eventSubscriber?: Subscription;

  constructor(
    protected perfReviewItemService: PerfReviewItemService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.perfReviewItemService.query().subscribe((res: HttpResponse<IPerfReviewItem[]>) => (this.perfReviewItems = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPerfReviewItems();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPerfReviewItem): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPerfReviewItems(): void {
    this.eventSubscriber = this.eventManager.subscribe('perfReviewItemListModification', () => this.loadAll());
  }

  delete(perfReviewItem: IPerfReviewItem): void {
    const modalRef = this.modalService.open(PerfReviewItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.perfReviewItem = perfReviewItem;
  }
}
