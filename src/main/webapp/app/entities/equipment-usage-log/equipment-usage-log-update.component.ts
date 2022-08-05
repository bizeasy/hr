import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEquipmentUsageLog, EquipmentUsageLog } from 'app/shared/model/equipment-usage-log.model';
import { EquipmentUsageLogService } from './equipment-usage-log.service';
import { IEquipment } from 'app/shared/model/equipment.model';
import { EquipmentService } from 'app/entities/equipment/equipment.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IEquipment | IUser;

@Component({
  selector: 'sys-equipment-usage-log-update',
  templateUrl: './equipment-usage-log-update.component.html',
})
export class EquipmentUsageLogUpdateComponent implements OnInit {
  isSaving = false;
  equipment: IEquipment[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    fromTime: [],
    toTime: [],
    remarks: [],
    equipment: [],
    cleanedBy: [],
    checkedBy: [],
  });

  constructor(
    protected equipmentUsageLogService: EquipmentUsageLogService,
    protected equipmentService: EquipmentService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ equipmentUsageLog }) => {
      if (!equipmentUsageLog.id) {
        const today = moment().startOf('day');
        equipmentUsageLog.fromTime = today;
        equipmentUsageLog.toTime = today;
      }

      this.updateForm(equipmentUsageLog);

      this.equipmentService.query().subscribe((res: HttpResponse<IEquipment[]>) => (this.equipment = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(equipmentUsageLog: IEquipmentUsageLog): void {
    this.editForm.patchValue({
      id: equipmentUsageLog.id,
      fromTime: equipmentUsageLog.fromTime ? equipmentUsageLog.fromTime.format(DATE_TIME_FORMAT) : null,
      toTime: equipmentUsageLog.toTime ? equipmentUsageLog.toTime.format(DATE_TIME_FORMAT) : null,
      remarks: equipmentUsageLog.remarks,
      equipment: equipmentUsageLog.equipment,
      cleanedBy: equipmentUsageLog.cleanedBy,
      checkedBy: equipmentUsageLog.checkedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const equipmentUsageLog = this.createFromForm();
    if (equipmentUsageLog.id !== undefined) {
      this.subscribeToSaveResponse(this.equipmentUsageLogService.update(equipmentUsageLog));
    } else {
      this.subscribeToSaveResponse(this.equipmentUsageLogService.create(equipmentUsageLog));
    }
  }

  private createFromForm(): IEquipmentUsageLog {
    return {
      ...new EquipmentUsageLog(),
      id: this.editForm.get(['id'])!.value,
      fromTime: this.editForm.get(['fromTime'])!.value ? moment(this.editForm.get(['fromTime'])!.value, DATE_TIME_FORMAT) : undefined,
      toTime: this.editForm.get(['toTime'])!.value ? moment(this.editForm.get(['toTime'])!.value, DATE_TIME_FORMAT) : undefined,
      remarks: this.editForm.get(['remarks'])!.value,
      equipment: this.editForm.get(['equipment'])!.value,
      cleanedBy: this.editForm.get(['cleanedBy'])!.value,
      checkedBy: this.editForm.get(['checkedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipmentUsageLog>>): void {
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
