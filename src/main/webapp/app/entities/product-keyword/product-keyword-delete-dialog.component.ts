import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductKeyword } from 'app/shared/model/product-keyword.model';
import { ProductKeywordService } from './product-keyword.service';

@Component({
  templateUrl: './product-keyword-delete-dialog.component.html',
})
export class ProductKeywordDeleteDialogComponent {
  productKeyword?: IProductKeyword;

  constructor(
    protected productKeywordService: ProductKeywordService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productKeywordService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productKeywordListModification');
      this.activeModal.close();
    });
  }
}
