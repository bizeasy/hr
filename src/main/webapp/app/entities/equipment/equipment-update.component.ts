import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEquipment, Equipment } from 'app/shared/model/equipment.model';
import { EquipmentService } from './equipment.service';
import { IEquipmentType } from 'app/shared/model/equipment-type.model';
import { EquipmentTypeService } from 'app/entities/equipment-type/equipment-type.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';

type SelectableEntity = IEquipmentType | IStatus;

@Component({
  selector: 'sys-equipment-update',
  templateUrl: './equipment-update.component.html',
})
export class EquipmentUpdateComponent implements OnInit {
  isSaving = false;
  equipmenttypes: IEquipmentType[] = [];
  statuses: IStatus[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(100)]],
    description: [null, [Validators.maxLength(200)]],
    equipmentNumber: [null, [Validators.maxLength(60)]],
    minCapacityRange: [],
    maxCapacityRange: [],
    minOperationalRange: [],
    maxOperationalRange: [],
    lastCalibratedDate: [],
    calibrationDueDate: [],
    changeControlNo: [null, [Validators.maxLength(60)]],
    equipmentType: [],
    status: [],
  });

  constructor(
    protected equipmentService: EquipmentService,
    protected equipmentTypeService: EquipmentTypeService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ equipment }) => {
      if (!equipment.id) {
        const today = moment().startOf('day');
        equipment.lastCalibratedDate = today;
        equipment.calibrationDueDate = today;
      }

      this.updateForm(equipment);

      this.equipmentTypeService.query().subscribe((res: HttpResponse<IEquipmentType[]>) => (this.equipmenttypes = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));
    });
  }

  updateForm(equipment: IEquipment): void {
    this.editForm.patchValue({
      id: equipment.id,
      name: equipment.name,
      description: equipment.description,
      equipmentNumber: equipment.equipmentNumber,
      minCapacityRange: equipment.minCapacityRange,
      maxCapacityRange: equipment.maxCapacityRange,
      minOperationalRange: equipment.minOperationalRange,
      maxOperationalRange: equipment.maxOperationalRange,
      lastCalibratedDate: equipment.lastCalibratedDate ? equipment.lastCalibratedDate.format(DATE_TIME_FORMAT) : null,
      calibrationDueDate: equipment.calibrationDueDate ? equipment.calibrationDueDate.format(DATE_TIME_FORMAT) : null,
      changeControlNo: equipment.changeControlNo,
      equipmentType: equipment.equipmentType,
      status: equipment.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const equipment = this.createFromForm();
    if (equipment.id !== undefined) {
      this.subscribeToSaveResponse(this.equipmentService.update(equipment));
    } else {
      this.subscribeToSaveResponse(this.equipmentService.create(equipment));
    }
  }

  private createFromForm(): IEquipment {
    return {
      ...new Equipment(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      equipmentNumber: this.editForm.get(['equipmentNumber'])!.value,
      minCapacityRange: this.editForm.get(['minCapacityRange'])!.value,
      maxCapacityRange: this.editForm.get(['maxCapacityRange'])!.value,
      minOperationalRange: this.editForm.get(['minOperationalRange'])!.value,
      maxOperationalRange: this.editForm.get(['maxOperationalRange'])!.value,
      lastCalibratedDate: this.editForm.get(['lastCalibratedDate'])!.value
        ? moment(this.editForm.get(['lastCalibratedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      calibrationDueDate: this.editForm.get(['calibrationDueDate'])!.value
        ? moment(this.editForm.get(['calibrationDueDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      changeControlNo: this.editForm.get(['changeControlNo'])!.value,
      equipmentType: this.editForm.get(['equipmentType'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipment>>): void {
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
