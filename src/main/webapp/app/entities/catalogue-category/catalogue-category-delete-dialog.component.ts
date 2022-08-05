import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatalogueCategory } from 'app/shared/model/catalogue-category.model';
import { CatalogueCategoryService } from './catalogue-category.service';

@Component({
  templateUrl: './catalogue-category-delete-dialog.component.html',
})
export class CatalogueCategoryDeleteDialogComponent {
  catalogueCategory?: ICatalogueCategory;

  constructor(
    protected catalogueCategoryService: CatalogueCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catalogueCategoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catalogueCategoryListModification');
      this.activeModal.close();
    });
  }
}
