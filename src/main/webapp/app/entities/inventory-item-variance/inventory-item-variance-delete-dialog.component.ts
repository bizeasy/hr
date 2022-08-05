import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryItemVariance } from 'app/shared/model/inventory-item-variance.model';
import { InventoryItemVarianceService } from './inventory-item-variance.service';

@Component({
  templateUrl: './inventory-item-variance-delete-dialog.component.html',
})
export class InventoryItemVarianceDeleteDialogComponent {
  inventoryItemVariance?: IInventoryItemVariance;

  constructor(
    protected inventoryItemVarianceService: InventoryItemVarianceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inventoryItemVarianceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('inventoryItemVarianceListModification');
      this.activeModal.close();
    });
  }
}
