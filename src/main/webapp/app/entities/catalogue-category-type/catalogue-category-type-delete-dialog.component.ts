import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatalogueCategoryType } from 'app/shared/model/catalogue-category-type.model';
import { CatalogueCategoryTypeService } from './catalogue-category-type.service';

@Component({
  templateUrl: './catalogue-category-type-delete-dialog.component.html',
})
export class CatalogueCategoryTypeDeleteDialogComponent {
  catalogueCategoryType?: ICatalogueCategoryType;

  constructor(
    protected catalogueCategoryTypeService: CatalogueCategoryTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catalogueCategoryTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catalogueCategoryTypeListModification');
      this.activeModal.close();
    });
  }
}
