import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPublicHolidays, PublicHolidays } from 'app/shared/model/public-holidays.model';
import { PublicHolidaysService } from './public-holidays.service';
import { IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeService } from 'app/entities/holiday-type/holiday-type.service';

@Component({
  selector: 'sys-public-holidays-update',
  templateUrl: './public-holidays-update.component.html',
})
export class PublicHolidaysUpdateComponent implements OnInit {
  isSaving = false;
  holidaytypes: IHolidayType[] = [];
  fromDateDp: any;
  thruDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    fromDate: [],
    thruDate: [],
    noOfHolidays: [],
    type: [],
  });

  constructor(
    protected publicHolidaysService: PublicHolidaysService,
    protected holidayTypeService: HolidayTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ publicHolidays }) => {
      this.updateForm(publicHolidays);

      this.holidayTypeService.query().subscribe((res: HttpResponse<IHolidayType[]>) => (this.holidaytypes = res.body || []));
    });
  }

  updateForm(publicHolidays: IPublicHolidays): void {
    this.editForm.patchValue({
      id: publicHolidays.id,
      name: publicHolidays.name,
      fromDate: publicHolidays.fromDate,
      thruDate: publicHolidays.thruDate,
      noOfHolidays: publicHolidays.noOfHolidays,
      type: publicHolidays.type,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const publicHolidays = this.createFromForm();
    if (publicHolidays.id !== undefined) {
      this.subscribeToSaveResponse(this.publicHolidaysService.update(publicHolidays));
    } else {
      this.subscribeToSaveResponse(this.publicHolidaysService.create(publicHolidays));
    }
  }

  private createFromForm(): IPublicHolidays {
    return {
      ...new PublicHolidays(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      thruDate: this.editForm.get(['thruDate'])!.value,
      noOfHolidays: this.editForm.get(['noOfHolidays'])!.value,
      type: this.editForm.get(['type'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPublicHolidays>>): void {
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

  trackById(index: number, item: IHolidayType): any {
    return item.id;
  }
}
