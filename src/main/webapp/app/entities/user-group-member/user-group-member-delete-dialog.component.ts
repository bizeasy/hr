import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserGroupMember } from 'app/shared/model/user-group-member.model';
import { UserGroupMemberService } from './user-group-member.service';

@Component({
  templateUrl: './user-group-member-delete-dialog.component.html',
})
export class UserGroupMemberDeleteDialogComponent {
  userGroupMember?: IUserGroupMember;

  constructor(
    protected userGroupMemberService: UserGroupMemberService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userGroupMemberService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userGroupMemberListModification');
      this.activeModal.close();
    });
  }
}
