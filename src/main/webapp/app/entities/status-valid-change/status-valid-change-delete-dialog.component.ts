import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStatusValidChange } from 'app/shared/model/status-valid-change.model';
import { StatusValidChangeService } from './status-valid-change.service';

@Component({
  templateUrl: './status-valid-change-delete-dialog.component.html',
})
export class StatusValidChangeDeleteDialogComponent {
  statusValidChange?: IStatusValidChange;

  constructor(
    protected statusValidChangeService: StatusValidChangeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.statusValidChangeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('statusValidChangeListModification');
      this.activeModal.close();
    });
  }
}
