import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserGroupAuthority } from 'app/shared/model/user-group-authority.model';

@Component({
  selector: 'sys-user-group-authority-detail',
  templateUrl: './user-group-authority-detail.component.html',
})
export class UserGroupAuthorityDetailComponent implements OnInit {
  userGroupAuthority: IUserGroupAuthority | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userGroupAuthority }) => (this.userGroupAuthority = userGroupAuthority));
  }

  previousState(): void {
    window.history.back();
  }
}
