import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserGroupMember, UserGroupMember } from 'app/shared/model/user-group-member.model';
import { UserGroupMemberService } from './user-group-member.service';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { UserGroupService } from 'app/entities/user-group/user-group.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IUserGroup | IUser;

@Component({
  selector: 'sys-user-group-member-update',
  templateUrl: './user-group-member-update.component.html',
})
export class UserGroupMemberUpdateComponent implements OnInit {
  isSaving = false;
  usergroups: IUserGroup[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    userGroup: [null, Validators.required],
    user: [null, Validators.required],
  });

  constructor(
    protected userGroupMemberService: UserGroupMemberService,
    protected userGroupService: UserGroupService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userGroupMember }) => {
      if (!userGroupMember.id) {
        const today = moment().startOf('day');
        userGroupMember.fromDate = today;
        userGroupMember.thruDate = today;
      }

      this.updateForm(userGroupMember);

      this.userGroupService.query().subscribe((res: HttpResponse<IUserGroup[]>) => (this.usergroups = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(userGroupMember: IUserGroupMember): void {
    this.editForm.patchValue({
      id: userGroupMember.id,
      fromDate: userGroupMember.fromDate ? userGroupMember.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: userGroupMember.thruDate ? userGroupMember.thruDate.format(DATE_TIME_FORMAT) : null,
      userGroup: userGroupMember.userGroup,
      user: userGroupMember.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userGroupMember = this.createFromForm();
    if (userGroupMember.id !== undefined) {
      this.subscribeToSaveResponse(this.userGroupMemberService.update(userGroupMember));
    } else {
      this.subscribeToSaveResponse(this.userGroupMemberService.create(userGroupMember));
    }
  }

  private createFromForm(): IUserGroupMember {
    return {
      ...new UserGroupMember(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? moment(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      userGroup: this.editForm.get(['userGroup'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserGroupMember>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
