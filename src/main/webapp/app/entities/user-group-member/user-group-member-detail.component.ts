import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserGroupMember } from 'app/shared/model/user-group-member.model';

@Component({
  selector: 'sys-user-group-member-detail',
  templateUrl: './user-group-member-detail.component.html',
})
export class UserGroupMemberDetailComponent implements OnInit {
  userGroupMember: IUserGroupMember | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userGroupMember }) => (this.userGroupMember = userGroupMember));
  }

  previousState(): void {
    window.history.back();
  }
}
