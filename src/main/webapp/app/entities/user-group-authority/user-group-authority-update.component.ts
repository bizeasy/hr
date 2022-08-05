import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserGroupAuthority, UserGroupAuthority } from 'app/shared/model/user-group-authority.model';
import { UserGroupAuthorityService } from './user-group-authority.service';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { UserGroupService } from 'app/entities/user-group/user-group.service';

@Component({
  selector: 'sys-user-group-authority-update',
  templateUrl: './user-group-authority-update.component.html',
})
export class UserGroupAuthorityUpdateComponent implements OnInit {
  isSaving = false;
  usergroups: IUserGroup[] = [];

  editForm = this.fb.group({
    id: [],
    authority: [],
    userGroup: [null, Validators.required],
  });

  constructor(
    protected userGroupAuthorityService: UserGroupAuthorityService,
    protected userGroupService: UserGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userGroupAuthority }) => {
      this.updateForm(userGroupAuthority);

      this.userGroupService.query().subscribe((res: HttpResponse<IUserGroup[]>) => (this.usergroups = res.body || []));
    });
  }

  updateForm(userGroupAuthority: IUserGroupAuthority): void {
    this.editForm.patchValue({
      id: userGroupAuthority.id,
      authority: userGroupAuthority.authority,
      userGroup: userGroupAuthority.userGroup,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userGroupAuthority = this.createFromForm();
    if (userGroupAuthority.id !== undefined) {
      this.subscribeToSaveResponse(this.userGroupAuthorityService.update(userGroupAuthority));
    } else {
      this.subscribeToSaveResponse(this.userGroupAuthorityService.create(userGroupAuthority));
    }
  }

  private createFromForm(): IUserGroupAuthority {
    return {
      ...new UserGroupAuthority(),
      id: this.editForm.get(['id'])!.value,
      authority: this.editForm.get(['authority'])!.value,
      userGroup: this.editForm.get(['userGroup'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserGroupAuthority>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUserGroup): any {
    return item.id;
  }
}
