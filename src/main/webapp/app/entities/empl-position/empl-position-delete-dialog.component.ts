import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmplPosition } from 'app/shared/model/empl-position.model';
import { EmplPositionService } from './empl-position.service';

@Component({
  templateUrl: './empl-position-delete-dialog.component.html',
})
export class EmplPositionDeleteDialogComponent {
  emplPosition?: IEmplPosition;

  constructor(
    protected emplPositionService: EmplPositionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emplPositionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('emplPositionListModification');
      this.activeModal.close();
    });
  }
}
