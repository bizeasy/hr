import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmplPositionReportingStruct } from 'app/shared/model/empl-position-reporting-struct.model';
import { EmplPositionReportingStructService } from './empl-position-reporting-struct.service';

@Component({
  templateUrl: './empl-position-reporting-struct-delete-dialog.component.html',
})
export class EmplPositionReportingStructDeleteDialogComponent {
  emplPositionReportingStruct?: IEmplPositionReportingStruct;

  constructor(
    protected emplPositionReportingStructService: EmplPositionReportingStructService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emplPositionReportingStructService.delete(id).subscribe(() => {
      this.eventManager.broadcast('emplPositionReportingStructListModification');
      this.activeModal.close();
    });
  }
}
