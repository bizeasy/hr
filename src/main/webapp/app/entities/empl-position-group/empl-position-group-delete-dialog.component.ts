import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmplPositionGroup } from 'app/shared/model/empl-position-group.model';
import { EmplPositionGroupService } from './empl-position-group.service';

@Component({
  templateUrl: './empl-position-group-delete-dialog.component.html',
})
export class EmplPositionGroupDeleteDialogComponent {
  emplPositionGroup?: IEmplPositionGroup;

  constructor(
    protected emplPositionGroupService: EmplPositionGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emplPositionGroupService.delete(id).subscribe(() => {
      this.eventManager.broadcast('emplPositionGroupListModification');
      this.activeModal.close();
    });
  }
}
