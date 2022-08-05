import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPermissionAuthority, PermissionAuthority } from 'app/shared/model/permission-authority.model';
import { PermissionAuthorityService } from './permission-authority.service';

@Component({
  selector: 'sys-permission-authority-update',
  templateUrl: './permission-authority-update.component.html',
})
export class PermissionAuthorityUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    authority: [],
  });

  constructor(
    protected permissionAuthorityService: PermissionAuthorityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ permissionAuthority }) => {
      this.updateForm(permissionAuthority);
    });
  }

  updateForm(permissionAuthority: IPermissionAuthority): void {
    this.editForm.patchValue({
      id: permissionAuthority.id,
      authority: permissionAuthority.authority,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const permissionAuthority = this.createFromForm();
    if (permissionAuthority.id !== undefined) {
      this.subscribeToSaveResponse(this.permissionAuthorityService.update(permissionAuthority));
    } else {
      this.subscribeToSaveResponse(this.permissionAuthorityService.create(permissionAuthority));
    }
  }

  private createFromForm(): IPermissionAuthority {
    return {
      ...new PermissionAuthority(),
      id: this.editForm.get(['id'])!.value,
      authority: this.editForm.get(['authority'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPermissionAuthority>>): void {
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
}
