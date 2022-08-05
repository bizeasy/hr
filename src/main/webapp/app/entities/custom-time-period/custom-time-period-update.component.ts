import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICustomTimePeriod, CustomTimePeriod } from 'app/shared/model/custom-time-period.model';
import { CustomTimePeriodService } from './custom-time-period.service';
import { IPeriodType } from 'app/shared/model/period-type.model';
import { PeriodTypeService } from 'app/entities/period-type/period-type.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';

type SelectableEntity = IPeriodType | IParty | ICustomTimePeriod;

@Component({
  selector: 'sys-custom-time-period-update',
  templateUrl: './custom-time-period-update.component.html',
})
export class CustomTimePeriodUpdateComponent implements OnInit {
  isSaving = false;
  periodtypes: IPeriodType[] = [];
  parties: IParty[] = [];
  customtimeperiods: ICustomTimePeriod[] = [];

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    isClosed: [],
    periodName: [],
    periodNum: [],
    periodType: [],
    organisationParty: [],
    parent: [],
  });

  constructor(
    protected customTimePeriodService: CustomTimePeriodService,
    protected periodTypeService: PeriodTypeService,
    protected partyService: PartyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customTimePeriod }) => {
      if (!customTimePeriod.id) {
        const today = moment().startOf('day');
        customTimePeriod.fromDate = today;
        customTimePeriod.thruDate = today;
      }

      this.updateForm(customTimePeriod);

      this.periodTypeService.query().subscribe((res: HttpResponse<IPeriodType[]>) => (this.periodtypes = res.body || []));

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.customTimePeriodService.query().subscribe((res: HttpResponse<ICustomTimePeriod[]>) => (this.customtimeperiods = res.body || []));
    });
  }

  updateForm(customTimePeriod: ICustomTimePeriod): void {
    this.editForm.patchValue({
      id: customTimePeriod.id,
      fromDate: customTimePeriod.fromDate ? customTimePeriod.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: customTimePeriod.thruDate ? customTimePeriod.thruDate.format(DATE_TIME_FORMAT) : null,
      isClosed: customTimePeriod.isClosed,
      periodName: customTimePeriod.periodName,
      periodNum: customTimePeriod.periodNum,
      periodType: customTimePeriod.periodType,
      organisationParty: customTimePeriod.organisationParty,
      parent: customTimePeriod.parent,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customTimePeriod = this.createFromForm();
    if (customTimePeriod.id !== undefined) {
      this.subscribeToSaveResponse(this.customTimePeriodService.update(customTimePeriod));
    } else {
      this.subscribeToSaveResponse(this.customTimePeriodService.create(customTimePeriod));
    }
  }

  private createFromForm(): ICustomTimePeriod {
    return {
      ...new CustomTimePeriod(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? moment(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      isClosed: this.editForm.get(['isClosed'])!.value,
      periodName: this.editForm.get(['periodName'])!.value,
      periodNum: this.editForm.get(['periodNum'])!.value,
      periodType: this.editForm.get(['periodType'])!.value,
      organisationParty: this.editForm.get(['organisationParty'])!.value,
      parent: this.editForm.get(['parent'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomTimePeriod>>): void {
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
