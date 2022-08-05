import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRoleType } from 'app/shared/model/role-type.model';
import { RoleTypeService } from './role-type.service';

@Component({
  templateUrl: './role-type-delete-dialog.component.html',
})
export class RoleTypeDeleteDialogComponent {
  roleType?: IRoleType;

  constructor(protected roleTypeService: RoleTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.roleTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('roleTypeListModification');
      this.activeModal.close();
    });
  }
}
