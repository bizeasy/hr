import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITimeSheet } from 'app/shared/model/time-sheet.model';
import { TimeSheetService } from './time-sheet.service';

@Component({
  templateUrl: './time-sheet-delete-dialog.component.html',
})
export class TimeSheetDeleteDialogComponent {
  timeSheet?: ITimeSheet;

  constructor(protected timeSheetService: TimeSheetService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.timeSheetService.delete(id).subscribe(() => {
      this.eventManager.broadcast('timeSheetListModification');
      this.activeModal.close();
    });
  }
}
