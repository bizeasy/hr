import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductCategoryType } from 'app/shared/model/product-category-type.model';
import { ProductCategoryTypeService } from './product-category-type.service';

@Component({
  templateUrl: './product-category-type-delete-dialog.component.html',
})
export class ProductCategoryTypeDeleteDialogComponent {
  productCategoryType?: IProductCategoryType;

  constructor(
    protected productCategoryTypeService: ProductCategoryTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productCategoryTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productCategoryTypeListModification');
      this.activeModal.close();
    });
  }
}
