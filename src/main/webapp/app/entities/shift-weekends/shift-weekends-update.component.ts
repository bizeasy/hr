import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IShiftWeekends, ShiftWeekends } from 'app/shared/model/shift-weekends.model';
import { ShiftWeekendsService } from './shift-weekends.service';
import { IShift } from 'app/shared/model/shift.model';
import { ShiftService } from 'app/entities/shift/shift.service';

@Component({
  selector: 'sys-shift-weekends-update',
  templateUrl: './shift-weekends-update.component.html',
})
export class ShiftWeekendsUpdateComponent implements OnInit {
  isSaving = false;
  shifts: IShift[] = [];
  fromDateDp: any;
  thruDateDp: any;

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    shift: [],
  });

  constructor(
    protected shiftWeekendsService: ShiftWeekendsService,
    protected shiftService: ShiftService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shiftWeekends }) => {
      this.updateForm(shiftWeekends);

      this.shiftService.query().subscribe((res: HttpResponse<IShift[]>) => (this.shifts = res.body || []));
    });
  }

  updateForm(shiftWeekends: IShiftWeekends): void {
    this.editForm.patchValue({
      id: shiftWeekends.id,
      fromDate: shiftWeekends.fromDate,
      thruDate: shiftWeekends.thruDate,
      shift: shiftWeekends.shift,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shiftWeekends = this.createFromForm();
    if (shiftWeekends.id !== undefined) {
      this.subscribeToSaveResponse(this.shiftWeekendsService.update(shiftWeekends));
    } else {
      this.subscribeToSaveResponse(this.shiftWeekendsService.create(shiftWeekends));
    }
  }

  private createFromForm(): IShiftWeekends {
    return {
      ...new ShiftWeekends(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      thruDate: this.editForm.get(['thruDate'])!.value,
      shift: this.editForm.get(['shift'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShiftWeekends>>): void {
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
