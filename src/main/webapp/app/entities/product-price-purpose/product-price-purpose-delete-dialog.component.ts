import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductPricePurpose } from 'app/shared/model/product-price-purpose.model';
import { ProductPricePurposeService } from './product-price-purpose.service';

@Component({
  templateUrl: './product-price-purpose-delete-dialog.component.html',
})
export class ProductPricePurposeDeleteDialogComponent {
  productPricePurpose?: IProductPricePurpose;

  constructor(
    protected productPricePurposeService: ProductPricePurposeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productPricePurposeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productPricePurposeListModification');
      this.activeModal.close();
    });
  }
}
