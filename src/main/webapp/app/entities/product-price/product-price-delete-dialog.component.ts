import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductPrice } from 'app/shared/model/product-price.model';
import { ProductPriceService } from './product-price.service';

@Component({
  templateUrl: './product-price-delete-dialog.component.html',
})
export class ProductPriceDeleteDialogComponent {
  productPrice?: IProductPrice;

  constructor(
    protected productPriceService: ProductPriceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productPriceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productPriceListModification');
      this.activeModal.close();
    });
  }
}
