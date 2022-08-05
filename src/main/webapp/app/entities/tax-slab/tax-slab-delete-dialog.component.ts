import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaxSlab } from 'app/shared/model/tax-slab.model';
import { TaxSlabService } from './tax-slab.service';

@Component({
  templateUrl: './tax-slab-delete-dialog.component.html',
})
export class TaxSlabDeleteDialogComponent {
  taxSlab?: ITaxSlab;

  constructor(protected taxSlabService: TaxSlabService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taxSlabService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taxSlabListModification');
      this.activeModal.close();
    });
  }
}
