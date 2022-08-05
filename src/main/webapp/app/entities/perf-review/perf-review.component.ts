import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPerfReview } from 'app/shared/model/perf-review.model';
import { PerfReviewService } from './perf-review.service';
import { PerfReviewDeleteDialogComponent } from './perf-review-delete-dialog.component';

@Component({
  selector: 'sys-perf-review',
  templateUrl: './perf-review.component.html',
})
export class PerfReviewComponent implements OnInit, OnDestroy {
  perfReviews?: IPerfReview[];
  eventSubscriber?: Subscription;

  constructor(protected perfReviewService: PerfReviewService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.perfReviewService.query().subscribe((res: HttpResponse<IPerfReview[]>) => (this.perfReviews = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPerfReviews();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPerfReview): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPerfReviews(): void {
    this.eventSubscriber = this.eventManager.subscribe('perfReviewListModification', () => this.loadAll());
  }

  delete(perfReview: IPerfReview): void {
    const modalRef = this.modalService.open(PerfReviewDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.perfReview = perfReview;
  }
}
