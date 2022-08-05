import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPayrollPreference } from 'app/shared/model/payroll-preference.model';
import { PayrollPreferenceService } from './payroll-preference.service';

@Component({
  templateUrl: './payroll-preference-delete-dialog.component.html',
})
export class PayrollPreferenceDeleteDialogComponent {
  payrollPreference?: IPayrollPreference;

  constructor(
    protected payrollPreferenceService: PayrollPreferenceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.payrollPreferenceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('payrollPreferenceListModification');
      this.activeModal.close();
    });
  }
}
