import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryItemDelegate } from 'app/shared/model/inventory-item-delegate.model';
import { InventoryItemDelegateService } from './inventory-item-delegate.service';

@Component({
  templateUrl: './inventory-item-delegate-delete-dialog.component.html',
})
export class InventoryItemDelegateDeleteDialogComponent {
  inventoryItemDelegate?: IInventoryItemDelegate;

  constructor(
    protected inventoryItemDelegateService: InventoryItemDelegateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inventoryItemDelegateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('inventoryItemDelegateListModification');
      this.activeModal.close();
    });
  }
}
