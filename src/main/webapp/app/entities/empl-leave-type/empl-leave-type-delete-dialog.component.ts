import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmplLeaveType } from 'app/shared/model/empl-leave-type.model';
import { EmplLeaveTypeService } from './empl-leave-type.service';

@Component({
  templateUrl: './empl-leave-type-delete-dialog.component.html',
})
export class EmplLeaveTypeDeleteDialogComponent {
  emplLeaveType?: IEmplLeaveType;

  constructor(
    protected emplLeaveTypeService: EmplLeaveTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emplLeaveTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('emplLeaveTypeListModification');
      this.activeModal.close();
    });
  }
}
