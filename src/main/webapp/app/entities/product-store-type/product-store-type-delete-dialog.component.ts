import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductStoreType } from 'app/shared/model/product-store-type.model';
import { ProductStoreTypeService } from './product-store-type.service';

@Component({
  templateUrl: './product-store-type-delete-dialog.component.html',
})
export class ProductStoreTypeDeleteDialogComponent {
  productStoreType?: IProductStoreType;

  constructor(
    protected productStoreTypeService: ProductStoreTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productStoreTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productStoreTypeListModification');
      this.activeModal.close();
    });
  }
}
