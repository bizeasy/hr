import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IShift, Shift } from 'app/shared/model/shift.model';
import { ShiftService } from './shift.service';

@Component({
  selector: 'sys-shift-update',
  templateUrl: './shift-update.component.html',
})
export class ShiftUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    fromTime: [],
    toTime: [],
    workingHrs: [],
    monthlyPaidLeaves: [],
  });

  constructor(protected shiftService: ShiftService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shift }) => {
      if (!shift.id) {
        const today = moment().startOf('day');
        shift.fromTime = today;
        shift.toTime = today;
      }

      this.updateForm(shift);
    });
  }

  updateForm(shift: IShift): void {
    this.editForm.patchValue({
      id: shift.id,
      name: shift.name,
      fromTime: shift.fromTime ? shift.fromTime.format(DATE_TIME_FORMAT) : null,
      toTime: shift.toTime ? shift.toTime.format(DATE_TIME_FORMAT) : null,
      workingHrs: shift.workingHrs,
      monthlyPaidLeaves: shift.monthlyPaidLeaves,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shift = this.createFromForm();
    if (shift.id !== undefined) {
      this.subscribeToSaveResponse(this.shiftService.update(shift));
    } else {
      this.subscribeToSaveResponse(this.shiftService.create(shift));
    }
  }

  private createFromForm(): IShift {
    return {
      ...new Shift(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      fromTime: this.editForm.get(['fromTime'])!.value ? moment(this.editForm.get(['fromTime'])!.value, DATE_TIME_FORMAT) : undefined,
      toTime: this.editForm.get(['toTime'])!.value ? moment(this.editForm.get(['toTime'])!.value, DATE_TIME_FORMAT) : undefined,
      workingHrs: this.editForm.get(['workingHrs'])!.value,
      monthlyPaidLeaves: this.editForm.get(['monthlyPaidLeaves'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShift>>): void {
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
