import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IShiftHolidays, ShiftHolidays } from 'app/shared/model/shift-holidays.model';
import { ShiftHolidaysService } from './shift-holidays.service';
import { IShift } from 'app/shared/model/shift.model';
import { ShiftService } from 'app/entities/shift/shift.service';

@Component({
  selector: 'sys-shift-holidays-update',
  templateUrl: './shift-holidays-update.component.html',
})
export class ShiftHolidaysUpdateComponent implements OnInit {
  isSaving = false;
  shifts: IShift[] = [];

  editForm = this.fb.group({
    id: [],
    dayOfheWeek: [],
    monthlyPaidLeaves: [],
    yearlyPaidLeaves: [],
    shift: [],
  });

  constructor(
    protected shiftHolidaysService: ShiftHolidaysService,
    protected shiftService: ShiftService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shiftHolidays }) => {
      this.updateForm(shiftHolidays);

      this.shiftService.query().subscribe((res: HttpResponse<IShift[]>) => (this.shifts = res.body || []));
    });
  }

  updateForm(shiftHolidays: IShiftHolidays): void {
    this.editForm.patchValue({
      id: shiftHolidays.id,
      dayOfheWeek: shiftHolidays.dayOfheWeek,
      monthlyPaidLeaves: shiftHolidays.monthlyPaidLeaves,
      yearlyPaidLeaves: shiftHolidays.yearlyPaidLeaves,
      shift: shiftHolidays.shift,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shiftHolidays = this.createFromForm();
    if (shiftHolidays.id !== undefined) {
      this.subscribeToSaveResponse(this.shiftHolidaysService.update(shiftHolidays));
    } else {
      this.subscribeToSaveResponse(this.shiftHolidaysService.create(shiftHolidays));
    }
  }

  private createFromForm(): IShiftHolidays {
    return {
      ...new ShiftHolidays(),
      id: this.editForm.get(['id'])!.value,
      dayOfheWeek: this.editForm.get(['dayOfheWeek'])!.value,
      monthlyPaidLeaves: this.editForm.get(['monthlyPaidLeaves'])!.value,
      yearlyPaidLeaves: this.editForm.get(['yearlyPaidLeaves'])!.value,
      shift: this.editForm.get(['shift'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShiftHolidays>>): void {
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

  trackById(index: number, item: IShift): any {
    return item.id;
  }
}
