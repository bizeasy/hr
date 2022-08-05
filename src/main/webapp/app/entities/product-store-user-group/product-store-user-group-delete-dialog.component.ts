import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductStoreUserGroup } from 'app/shared/model/product-store-user-group.model';
import { ProductStoreUserGroupService } from './product-store-user-group.service';

@Component({
  templateUrl: './product-store-user-group-delete-dialog.component.html',
})
export class ProductStoreUserGroupDeleteDialogComponent {
  productStoreUserGroup?: IProductStoreUserGroup;

  constructor(
    protected productStoreUserGroupService: ProductStoreUserGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productStoreUserGroupService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productStoreUserGroupListModification');
      this.activeModal.close();
    });
  }
}
