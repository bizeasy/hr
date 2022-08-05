import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPermissionAuthority } from 'app/shared/model/permission-authority.model';
import { PermissionAuthorityService } from './permission-authority.service';

@Component({
  templateUrl: './permission-authority-delete-dialog.component.html',
})
export class PermissionAuthorityDeleteDialogComponent {
  permissionAuthority?: IPermissionAuthority;

  constructor(
    protected permissionAuthorityService: PermissionAuthorityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.permissionAuthorityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('permissionAuthorityListModification');
      this.activeModal.close();
    });
  }
}
