import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomTimePeriod } from 'app/shared/model/custom-time-period.model';
import { CustomTimePeriodService } from './custom-time-period.service';

@Component({
  templateUrl: './custom-time-period-delete-dialog.component.html',
})
export class CustomTimePeriodDeleteDialogComponent {
  customTimePeriod?: ICustomTimePeriod;

  constructor(
    protected customTimePeriodService: CustomTimePeriodService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customTimePeriodService.delete(id).subscribe(() => {
      this.eventManager.broadcast('customTimePeriodListModification');
      this.activeModal.close();
    });
  }
}
