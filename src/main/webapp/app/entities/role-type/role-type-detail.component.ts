import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRoleType } from 'app/shared/model/role-type.model';

@Component({
  selector: 'sys-role-type-detail',
  templateUrl: './role-type-detail.component.html',
})
export class RoleTypeDetailComponent implements OnInit {
  roleType: IRoleType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ roleType }) => (this.roleType = roleType));
  }

  previousState(): void {
    window.history.back();
  }
}
