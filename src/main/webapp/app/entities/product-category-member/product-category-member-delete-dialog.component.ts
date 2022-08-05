import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductCategoryMember } from 'app/shared/model/product-category-member.model';
import { ProductCategoryMemberService } from './product-category-member.service';

@Component({
  templateUrl: './product-category-member-delete-dialog.component.html',
})
export class ProductCategoryMemberDeleteDialogComponent {
  productCategoryMember?: IProductCategoryMember;

  constructor(
    protected productCategoryMemberService: ProductCategoryMemberService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productCategoryMemberService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productCategoryMemberListModification');
      this.activeModal.close();
    });
  }
}
