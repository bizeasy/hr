import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkEffortProduct } from 'app/shared/model/work-effort-product.model';
import { WorkEffortProductService } from './work-effort-product.service';

@Component({
  templateUrl: './work-effort-product-delete-dialog.component.html',
})
export class WorkEffortProductDeleteDialogComponent {
  workEffortProduct?: IWorkEffortProduct;

  constructor(
    protected workEffortProductService: WorkEffortProductService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workEffortProductService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workEffortProductListModification');
      this.activeModal.close();
    });
  }
}
