import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductPriceType } from 'app/shared/model/product-price-type.model';
import { ProductPriceTypeService } from './product-price-type.service';

@Component({
  templateUrl: './product-price-type-delete-dialog.component.html',
})
export class ProductPriceTypeDeleteDialogComponent {
  productPriceType?: IProductPriceType;

  constructor(
    protected productPriceTypeService: ProductPriceTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productPriceTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productPriceTypeListModification');
      this.activeModal.close();
    });
  }
}
