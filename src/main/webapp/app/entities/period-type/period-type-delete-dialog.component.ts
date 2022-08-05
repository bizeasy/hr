import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPeriodType } from 'app/shared/model/period-type.model';
import { PeriodTypeService } from './period-type.service';

@Component({
  templateUrl: './period-type-delete-dialog.component.html',
})
export class PeriodTypeDeleteDialogComponent {
  periodType?: IPeriodType;

  constructor(
    protected periodTypeService: PeriodTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.periodTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('periodTypeListModification');
      this.activeModal.close();
    });
  }
}
