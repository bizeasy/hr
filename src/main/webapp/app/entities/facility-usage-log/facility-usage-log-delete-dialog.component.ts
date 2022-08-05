import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFacilityUsageLog } from 'app/shared/model/facility-usage-log.model';
import { FacilityUsageLogService } from './facility-usage-log.service';

@Component({
  templateUrl: './facility-usage-log-delete-dialog.component.html',
})
export class FacilityUsageLogDeleteDialogComponent {
  facilityUsageLog?: IFacilityUsageLog;

  constructor(
    protected facilityUsageLogService: FacilityUsageLogService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.facilityUsageLogService.delete(id).subscribe(() => {
      this.eventManager.broadcast('facilityUsageLogListModification');
      this.activeModal.close();
    });
  }
}
