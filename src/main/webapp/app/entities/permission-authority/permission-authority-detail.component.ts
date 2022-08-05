import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPermissionAuthority } from 'app/shared/model/permission-authority.model';

@Component({
  selector: 'sys-permission-authority-detail',
  templateUrl: './permission-authority-detail.component.html',
})
export class PermissionAuthorityDetailComponent implements OnInit {
  permissionAuthority: IPermissionAuthority | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ permissionAuthority }) => (this.permissionAuthority = permissionAuthority));
  }

  previousState(): void {
    window.history.back();
  }
}
