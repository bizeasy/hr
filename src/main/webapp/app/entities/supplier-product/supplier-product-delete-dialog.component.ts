import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplierProduct } from 'app/shared/model/supplier-product.model';
import { SupplierProductService } from './supplier-product.service';

@Component({
  templateUrl: './supplier-product-delete-dialog.component.html',
})
export class SupplierProductDeleteDialogComponent {
  supplierProduct?: ISupplierProduct;

  constructor(
    protected supplierProductService: SupplierProductService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.supplierProductService.delete(id).subscribe(() => {
      this.eventManager.broadcast('supplierProductListModification');
      this.activeModal.close();
    });
  }
}
