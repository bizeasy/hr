import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmplLeaveReasonType } from 'app/shared/model/empl-leave-reason-type.model';
import { EmplLeaveReasonTypeService } from './empl-leave-reason-type.service';

@Component({
  templateUrl: './empl-leave-reason-type-delete-dialog.component.html',
})
export class EmplLeaveReasonTypeDeleteDialogComponent {
  emplLeaveReasonType?: IEmplLeaveReasonType;

  constructor(
    protected emplLeaveReasonTypeService: EmplLeaveReasonTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emplLeaveReasonTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('emplLeaveReasonTypeListModification');
      this.activeModal.close();
    });
  }
}
