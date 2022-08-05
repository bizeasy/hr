import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmplPositionTypeRate, EmplPositionTypeRate } from 'app/shared/model/empl-position-type-rate.model';
import { EmplPositionTypeRateService } from './empl-position-type-rate.service';
import { IRateType } from 'app/shared/model/rate-type.model';
import { RateTypeService } from 'app/entities/rate-type/rate-type.service';
import { IEmplPositionType } from 'app/shared/model/empl-position-type.model';
import { EmplPositionTypeService } from 'app/entities/empl-position-type/empl-position-type.service';
import { IPayGrade } from 'app/shared/model/pay-grade.model';
import { PayGradeService } from 'app/entities/pay-grade/pay-grade.service';

type SelectableEntity = IRateType | IEmplPositionType | IPayGrade;

@Component({
  selector: 'sys-empl-position-type-rate-update',
  templateUrl: './empl-position-type-rate-update.component.html',
})
export class EmplPositionTypeRateUpdateComponent implements OnInit {
  isSaving = false;
  ratetypes: IRateType[] = [];
  emplpositiontypes: IEmplPositionType[] = [];
  paygrades: IPayGrade[] = [];
  fromDateDp: any;
  thruDateDp: any;

  editForm = this.fb.group({
    id: [],
    rateAmount: [],
    fromDate: [],
    thruDate: [],
    rateType: [],
    emplPositionType: [],
    payGrade: [],
  });

  constructor(
    protected emplPositionTypeRateService: EmplPositionTypeRateService,
    protected rateTypeService: RateTypeService,
    protected emplPositionTypeService: EmplPositionTypeService,
    protected payGradeService: PayGradeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplPositionTypeRate }) => {
      this.updateForm(emplPositionTypeRate);

      this.rateTypeService.query().subscribe((res: HttpResponse<IRateType[]>) => (this.ratetypes = res.body || []));

      this.emplPositionTypeService.query().subscribe((res: HttpResponse<IEmplPositionType[]>) => (this.emplpositiontypes = res.body || []));

      this.payGradeService.query().subscribe((res: HttpResponse<IPayGrade[]>) => (this.paygrades = res.body || []));
    });
  }

  updateForm(emplPositionTypeRate: IEmplPositionTypeRate): void {
    this.editForm.patchValue({
      id: emplPositionTypeRate.id,
      rateAmount: emplPositionTypeRate.rateAmount,
      fromDate: emplPositionTypeRate.fromDate,
      thruDate: emplPositionTypeRate.thruDate,
      rateType: emplPositionTypeRate.rateType,
      emplPositionType: emplPositionTypeRate.emplPositionType,
      payGrade: emplPositionTypeRate.payGrade,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emplPositionTypeRate = this.createFromForm();
    if (emplPositionTypeRate.id !== undefined) {
      this.subscribeToSaveResponse(this.emplPositionTypeRateService.update(emplPositionTypeRate));
    } else {
      this.subscribeToSaveResponse(this.emplPositionTypeRateService.create(emplPositionTypeRate));
    }
  }

  private createFromForm(): IEmplPositionTypeRate {
    return {
      ...new EmplPositionTypeRate(),
      id: this.editForm.get(['id'])!.value,
      rateAmount: this.editForm.get(['rateAmount'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      thruDate: this.editForm.get(['thruDate'])!.value,
      rateType: this.editForm.get(['rateType'])!.value,
      emplPositionType: this.editForm.get(['emplPositionType'])!.value,
      payGrade: this.editForm.get(['payGrade'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmplPositionTypeRate>>): void {
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
