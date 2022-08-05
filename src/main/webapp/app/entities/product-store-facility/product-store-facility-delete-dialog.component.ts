import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductStoreFacility } from 'app/shared/model/product-store-facility.model';
import { ProductStoreFacilityService } from './product-store-facility.service';

@Component({
  templateUrl: './product-store-facility-delete-dialog.component.html',
})
export class ProductStoreFacilityDeleteDialogComponent {
  productStoreFacility?: IProductStoreFacility;

  constructor(
    protected productStoreFacilityService: ProductStoreFacilityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productStoreFacilityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productStoreFacilityListModification');
      this.activeModal.close();
    });
  }
}
