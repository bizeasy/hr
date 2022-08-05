import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryItemType } from 'app/shared/model/inventory-item-type.model';
import { InventoryItemTypeService } from './inventory-item-type.service';

@Component({
  templateUrl: './inventory-item-type-delete-dialog.component.html',
})
export class InventoryItemTypeDeleteDialogComponent {
  inventoryItemType?: IInventoryItemType;

  constructor(
    protected inventoryItemTypeService: InventoryItemTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inventoryItemTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('inventoryItemTypeListModification');
      this.activeModal.close();
    });
  }
}
