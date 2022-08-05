import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFacilityUsageLog, FacilityUsageLog } from 'app/shared/model/facility-usage-log.model';
import { FacilityUsageLogService } from './facility-usage-log.service';
import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from 'app/entities/facility/facility.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IFacility | IUser;

@Component({
  selector: 'sys-facility-usage-log-update',
  templateUrl: './facility-usage-log-update.component.html',
})
export class FacilityUsageLogUpdateComponent implements OnInit {
  isSaving = false;
  facilities: IFacility[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    fromTime: [],
    toTime: [],
    remarks: [],
    facility: [],
    cleanedBy: [],
    checkedBy: [],
  });

  constructor(
    protected facilityUsageLogService: FacilityUsageLogService,
    protected facilityService: FacilityService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facilityUsageLog }) => {
      if (!facilityUsageLog.id) {
        const today = moment().startOf('day');
        facilityUsageLog.fromTime = today;
        facilityUsageLog.toTime = today;
      }

      this.updateForm(facilityUsageLog);

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(facilityUsageLog: IFacilityUsageLog): void {
    this.editForm.patchValue({
      id: facilityUsageLog.id,
      fromTime: facilityUsageLog.fromTime ? facilityUsageLog.fromTime.format(DATE_TIME_FORMAT) : null,
      toTime: facilityUsageLog.toTime ? facilityUsageLog.toTime.format(DATE_TIME_FORMAT) : null,
      remarks: facilityUsageLog.remarks,
      facility: facilityUsageLog.facility,
      cleanedBy: facilityUsageLog.cleanedBy,
      checkedBy: facilityUsageLog.checkedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facilityUsageLog = this.createFromForm();
    if (facilityUsageLog.id !== undefined) {
      this.subscribeToSaveResponse(this.facilityUsageLogService.update(facilityUsageLog));
    } else {
      this.subscribeToSaveResponse(this.facilityUsageLogService.create(facilityUsageLog));
    }
  }

  private createFromForm(): IFacilityUsageLog {
    return {
      ...new FacilityUsageLog(),
      id: this.editForm.get(['id'])!.value,
      fromTime: this.editForm.get(['fromTime'])!.value ? moment(this.editForm.get(['fromTime'])!.value, DATE_TIME_FORMAT) : undefined,
      toTime: this.editForm.get(['toTime'])!.value ? moment(this.editForm.get(['toTime'])!.value, DATE_TIME_FORMAT) : undefined,
      remarks: this.editForm.get(['remarks'])!.value,
      facility: this.editForm.get(['facility'])!.value,
      cleanedBy: this.editForm.get(['cleanedBy'])!.value,
      checkedBy: this.editForm.get(['checkedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacilityUsageLog>>): void {
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
