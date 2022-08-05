import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryTransfer } from 'app/shared/model/inventory-transfer.model';
import { InventoryTransferService } from './inventory-transfer.service';

@Component({
  templateUrl: './inventory-transfer-delete-dialog.component.html',
})
export class InventoryTransferDeleteDialogComponent {
  inventoryTransfer?: IInventoryTransfer;

  constructor(
    protected inventoryTransferService: InventoryTransferService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inventoryTransferService.delete(id).subscribe(() => {
      this.eventManager.broadcast('inventoryTransferListModification');
      this.activeModal.close();
    });
  }
}
