import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmplPositionType } from 'app/shared/model/empl-position-type.model';
import { EmplPositionTypeService } from './empl-position-type.service';

@Component({
  templateUrl: './empl-position-type-delete-dialog.component.html',
})
export class EmplPositionTypeDeleteDialogComponent {
  emplPositionType?: IEmplPositionType;

  constructor(
    protected emplPositionTypeService: EmplPositionTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emplPositionTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('emplPositionTypeListModification');
      this.activeModal.close();
    });
  }
}
