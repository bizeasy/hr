import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITimeSheet, TimeSheet } from 'app/shared/model/time-sheet.model';
import { TimeSheetService } from './time-sheet.service';
import { ICustomTimePeriod } from 'app/shared/model/custom-time-period.model';
import { CustomTimePeriodService } from 'app/entities/custom-time-period/custom-time-period.service';

@Component({
  selector: 'sys-time-sheet-update',
  templateUrl: './time-sheet-update.component.html',
})
export class TimeSheetUpdateComponent implements OnInit {
  isSaving = false;
  customtimeperiods: ICustomTimePeriod[] = [];

  editForm = this.fb.group({
    id: [],
    overTimeDays: [],
    leaves: [],
    unappliedLeaves: [],
    halfDays: [],
    totalWorkingHours: [],
    timePeriod: [],
  });

  constructor(
    protected timeSheetService: TimeSheetService,
    protected customTimePeriodService: CustomTimePeriodService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ timeSheet }) => {
      this.updateForm(timeSheet);

      this.customTimePeriodService.query().subscribe((res: HttpResponse<ICustomTimePeriod[]>) => (this.customtimeperiods = res.body || []));
    });
  }

  updateForm(timeSheet: ITimeSheet): void {
    this.editForm.patchValue({
      id: timeSheet.id,
      overTimeDays: timeSheet.overTimeDays,
      leaves: timeSheet.leaves,
      unappliedLeaves: timeSheet.unappliedLeaves,
      halfDays: timeSheet.halfDays,
      totalWorkingHours: timeSheet.totalWorkingHours,
      timePeriod: timeSheet.timePeriod,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const timeSheet = this.createFromForm();
    if (timeSheet.id !== undefined) {
      this.subscribeToSaveResponse(this.timeSheetService.update(timeSheet));
    } else {
      this.subscribeToSaveResponse(this.timeSheetService.create(timeSheet));
    }
  }

  private createFromForm(): ITimeSheet {
    return {
      ...new TimeSheet(),
      id: this.editForm.get(['id'])!.value,
      overTimeDays: this.editForm.get(['overTimeDays'])!.value,
      leaves: this.editForm.get(['leaves'])!.value,
      unappliedLeaves: this.editForm.get(['unappliedLeaves'])!.value,
      halfDays: this.editForm.get(['halfDays'])!.value,
      totalWorkingHours: this.editForm.get(['totalWorkingHours'])!.value,
      timePeriod: this.editForm.get(['timePeriod'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITimeSheet>>): void {
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

  trackById(index: number, item: ICustomTimePeriod): any {
    return item.id;
  }
}
