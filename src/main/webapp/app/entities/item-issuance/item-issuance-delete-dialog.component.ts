import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemIssuance } from 'app/shared/model/item-issuance.model';
import { ItemIssuanceService } from './item-issuance.service';

@Component({
  templateUrl: './item-issuance-delete-dialog.component.html',
})
export class ItemIssuanceDeleteDialogComponent {
  itemIssuance?: IItemIssuance;

  constructor(
    protected itemIssuanceService: ItemIssuanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.itemIssuanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('itemIssuanceListModification');
      this.activeModal.close();
    });
  }
}
