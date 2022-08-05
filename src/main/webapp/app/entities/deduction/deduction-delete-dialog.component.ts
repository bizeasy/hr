import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeduction } from 'app/shared/model/deduction.model';
import { DeductionService } from './deduction.service';

@Component({
  templateUrl: './deduction-delete-dialog.component.html',
})
export class DeductionDeleteDialogComponent {
  deduction?: IDeduction;

  constructor(protected deductionService: DeductionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deductionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('deductionListModification');
      this.activeModal.close();
    });
  }
}
