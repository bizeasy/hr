import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserGroupAuthority } from 'app/shared/model/user-group-authority.model';
import { UserGroupAuthorityService } from './user-group-authority.service';

@Component({
  templateUrl: './user-group-authority-delete-dialog.component.html',
})
export class UserGroupAuthorityDeleteDialogComponent {
  userGroupAuthority?: IUserGroupAuthority;

  constructor(
    protected userGroupAuthorityService: UserGroupAuthorityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userGroupAuthorityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userGroupAuthorityListModification');
      this.activeModal.close();
    });
  }
}
