import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaxAuthority } from 'app/shared/model/tax-authority.model';
import { TaxAuthorityService } from './tax-authority.service';

@Component({
  templateUrl: './tax-authority-delete-dialog.component.html',
})
export class TaxAuthorityDeleteDialogComponent {
  taxAuthority?: ITaxAuthority;

  constructor(
    protected taxAuthorityService: TaxAuthorityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taxAuthorityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taxAuthorityListModification');
      this.activeModal.close();
    });
  }
}
