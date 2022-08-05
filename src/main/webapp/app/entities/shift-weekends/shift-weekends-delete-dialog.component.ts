import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShiftWeekends } from 'app/shared/model/shift-weekends.model';
import { ShiftWeekendsService } from './shift-weekends.service';

@Component({
  templateUrl: './shift-weekends-delete-dialog.component.html',
})
export class ShiftWeekendsDeleteDialogComponent {
  shiftWeekends?: IShiftWeekends;

  constructor(
    protected shiftWeekendsService: ShiftWeekendsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.shiftWeekendsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('shiftWeekendsListModification');
      this.activeModal.close();
    });
  }
}
