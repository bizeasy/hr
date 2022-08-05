import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaxAuthorityRateType } from 'app/shared/model/tax-authority-rate-type.model';
import { TaxAuthorityRateTypeService } from './tax-authority-rate-type.service';

@Component({
  templateUrl: './tax-authority-rate-type-delete-dialog.component.html',
})
export class TaxAuthorityRateTypeDeleteDialogComponent {
  taxAuthorityRateType?: ITaxAuthorityRateType;

  constructor(
    protected taxAuthorityRateTypeService: TaxAuthorityRateTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taxAuthorityRateTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taxAuthorityRateTypeListModification');
      this.activeModal.close();
    });
  }
}
