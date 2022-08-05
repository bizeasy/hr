import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPerfRatingType } from 'app/shared/model/perf-rating-type.model';
import { PerfRatingTypeService } from './perf-rating-type.service';

@Component({
  templateUrl: './perf-rating-type-delete-dialog.component.html',
})
export class PerfRatingTypeDeleteDialogComponent {
  perfRatingType?: IPerfRatingType;

  constructor(
    protected perfRatingTypeService: PerfRatingTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.perfRatingTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('perfRatingTypeListModification');
      this.activeModal.close();
    });
  }
}
