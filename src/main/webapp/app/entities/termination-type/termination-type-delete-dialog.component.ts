import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITerminationType } from 'app/shared/model/termination-type.model';
import { TerminationTypeService } from './termination-type.service';

@Component({
  templateUrl: './termination-type-delete-dialog.component.html',
})
export class TerminationTypeDeleteDialogComponent {
  terminationType?: ITerminationType;

  constructor(
    protected terminationTypeService: TerminationTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.terminationTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('terminationTypeListModification');
      this.activeModal.close();
    });
  }
}
