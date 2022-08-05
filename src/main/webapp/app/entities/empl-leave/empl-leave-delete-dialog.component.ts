import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmplLeave } from 'app/shared/model/empl-leave.model';
import { EmplLeaveService } from './empl-leave.service';

@Component({
  templateUrl: './empl-leave-delete-dialog.component.html',
})
export class EmplLeaveDeleteDialogComponent {
  emplLeave?: IEmplLeave;

  constructor(protected emplLeaveService: EmplLeaveService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emplLeaveService.delete(id).subscribe(() => {
      this.eventManager.broadcast('emplLeaveListModification');
      this.activeModal.close();
    });
  }
}
