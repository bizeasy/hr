import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITermType } from 'app/shared/model/term-type.model';
import { TermTypeService } from './term-type.service';

@Component({
  templateUrl: './term-type-delete-dialog.component.html',
})
export class TermTypeDeleteDialogComponent {
  termType?: ITermType;

  constructor(protected termTypeService: TermTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.termTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('termTypeListModification');
      this.activeModal.close();
    });
  }
}
