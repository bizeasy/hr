import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPerfReviewItemType } from 'app/shared/model/perf-review-item-type.model';
import { PerfReviewItemTypeService } from './perf-review-item-type.service';

@Component({
  templateUrl: './perf-review-item-type-delete-dialog.component.html',
})
export class PerfReviewItemTypeDeleteDialogComponent {
  perfReviewItemType?: IPerfReviewItemType;

  constructor(
    protected perfReviewItemTypeService: PerfReviewItemTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.perfReviewItemTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('perfReviewItemTypeListModification');
      this.activeModal.close();
    });
  }
}
