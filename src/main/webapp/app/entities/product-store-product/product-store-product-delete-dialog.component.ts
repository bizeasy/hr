import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductStoreProduct } from 'app/shared/model/product-store-product.model';
import { ProductStoreProductService } from './product-store-product.service';

@Component({
  templateUrl: './product-store-product-delete-dialog.component.html',
})
export class ProductStoreProductDeleteDialogComponent {
  productStoreProduct?: IProductStoreProduct;

  constructor(
    protected productStoreProductService: ProductStoreProductService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productStoreProductService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productStoreProductListModification');
      this.activeModal.close();
    });
  }
}
