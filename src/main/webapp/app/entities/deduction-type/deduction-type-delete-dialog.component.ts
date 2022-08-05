import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeductionType } from 'app/shared/model/deduction-type.model';
import { DeductionTypeService } from './deduction-type.service';

@Component({
  templateUrl: './deduction-type-delete-dialog.component.html',
})
export class DeductionTypeDeleteDialogComponent {
  deductionType?: IDeductionType;

  constructor(
    protected deductionTypeService: DeductionTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deductionTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('deductionTypeListModification');
      this.activeModal.close();
    });
  }
}
