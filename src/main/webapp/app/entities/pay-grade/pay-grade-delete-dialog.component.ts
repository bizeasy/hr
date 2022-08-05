import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPayGrade } from 'app/shared/model/pay-grade.model';
import { PayGradeService } from './pay-grade.service';

@Component({
  templateUrl: './pay-grade-delete-dialog.component.html',
})
export class PayGradeDeleteDialogComponent {
  payGrade?: IPayGrade;

  constructor(protected payGradeService: PayGradeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.payGradeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('payGradeListModification');
      this.activeModal.close();
    });
  }
}
