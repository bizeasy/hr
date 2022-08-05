import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvoiceType } from 'app/shared/model/invoice-type.model';
import { InvoiceTypeService } from './invoice-type.service';

@Component({
  templateUrl: './invoice-type-delete-dialog.component.html',
})
export class InvoiceTypeDeleteDialogComponent {
  invoiceType?: IInvoiceType;

  constructor(
    protected invoiceTypeService: InvoiceTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.invoiceTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('invoiceTypeListModification');
      this.activeModal.close();
    });
  }
}
