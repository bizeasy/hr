import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPerfReviewItem } from 'app/shared/model/perf-review-item.model';
import { PerfReviewItemService } from './perf-review-item.service';

@Component({
  templateUrl: './perf-review-item-delete-dialog.component.html',
})
export class PerfReviewItemDeleteDialogComponent {
  perfReviewItem?: IPerfReviewItem;

  constructor(
    protected perfReviewItemService: PerfReviewItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.perfReviewItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('perfReviewItemListModification');
      this.activeModal.close();
    });
  }
}
