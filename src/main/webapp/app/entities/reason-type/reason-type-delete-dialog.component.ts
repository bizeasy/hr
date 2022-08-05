import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReasonType } from 'app/shared/model/reason-type.model';
import { ReasonTypeService } from './reason-type.service';

@Component({
  templateUrl: './reason-type-delete-dialog.component.html',
})
export class ReasonTypeDeleteDialogComponent {
  reasonType?: IReasonType;

  constructor(
    protected reasonTypeService: ReasonTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reasonTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('reasonTypeListModification');
      this.activeModal.close();
    });
  }
}
