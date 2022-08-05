import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPerfReview } from 'app/shared/model/perf-review.model';
import { PerfReviewService } from './perf-review.service';

@Component({
  templateUrl: './perf-review-delete-dialog.component.html',
})
export class PerfReviewDeleteDialogComponent {
  perfReview?: IPerfReview;

  constructor(
    protected perfReviewService: PerfReviewService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.perfReviewService.delete(id).subscribe(() => {
      this.eventManager.broadcast('perfReviewListModification');
      this.activeModal.close();
    });
  }
}
