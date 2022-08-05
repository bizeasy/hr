import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductFacility } from 'app/shared/model/product-facility.model';
import { ProductFacilityService } from './product-facility.service';

@Component({
  templateUrl: './product-facility-delete-dialog.component.html',
})
export class ProductFacilityDeleteDialogComponent {
  productFacility?: IProductFacility;

  constructor(
    protected productFacilityService: ProductFacilityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productFacilityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productFacilityListModification');
      this.activeModal.close();
    });
  }
}
