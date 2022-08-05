import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKeywordType } from 'app/shared/model/keyword-type.model';
import { KeywordTypeService } from './keyword-type.service';

@Component({
  templateUrl: './keyword-type-delete-dialog.component.html',
})
export class KeywordTypeDeleteDialogComponent {
  keywordType?: IKeywordType;

  constructor(
    protected keywordTypeService: KeywordTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.keywordTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('keywordTypeListModification');
      this.activeModal.close();
    });
  }
}
