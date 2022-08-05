import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvoiceItemType } from 'app/shared/model/invoice-item-type.model';
import { InvoiceItemTypeService } from './invoice-item-type.service';

@Component({
  templateUrl: './invoice-item-type-delete-dialog.component.html',
})
export class InvoiceItemTypeDeleteDialogComponent {
  invoiceItemType?: IInvoiceItemType;

  constructor(
    protected invoiceItemTypeService: InvoiceItemTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.invoiceItemTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('invoiceItemTypeListModification');
      this.activeModal.close();
    });
  }
}
