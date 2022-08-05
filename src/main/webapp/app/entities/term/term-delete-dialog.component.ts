import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITerm } from 'app/shared/model/term.model';
import { TermService } from './term.service';

@Component({
  templateUrl: './term-delete-dialog.component.html',
})
export class TermDeleteDialogComponent {
  term?: ITerm;

  constructor(protected termService: TermService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.termService.delete(id).subscribe(() => {
      this.eventManager.broadcast('termListModification');
      this.activeModal.close();
    });
  }
}
