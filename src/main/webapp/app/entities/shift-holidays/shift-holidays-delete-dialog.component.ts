import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShiftHolidays } from 'app/shared/model/shift-holidays.model';
import { ShiftHolidaysService } from './shift-holidays.service';

@Component({
  templateUrl: './shift-holidays-delete-dialog.component.html',
})
export class ShiftHolidaysDeleteDialogComponent {
  shiftHolidays?: IShiftHolidays;

  constructor(
    protected shiftHolidaysService: ShiftHolidaysService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.shiftHolidaysService.delete(id).subscribe(() => {
      this.eventManager.broadcast('shiftHolidaysListModification');
      this.activeModal.close();
    });
  }
}
