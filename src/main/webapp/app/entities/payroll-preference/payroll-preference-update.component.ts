import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPayrollPreference, PayrollPreference } from 'app/shared/model/payroll-preference.model';
import { PayrollPreferenceService } from './payroll-preference.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IDeductionType } from 'app/shared/model/deduction-type.model';
import { DeductionTypeService } from 'app/entities/deduction-type/deduction-type.service';
import { IPaymentMethodType } from 'app/shared/model/payment-method-type.model';
import { PaymentMethodTypeService } from 'app/entities/payment-method-type/payment-method-type.service';
import { IPeriodType } from 'app/shared/model/period-type.model';
import { PeriodTypeService } from 'app/entities/period-type/period-type.service';

type SelectableEntity = IParty | IDeductionType | IPaymentMethodType | IPeriodType;

@Component({
  selector: 'sys-payroll-preference-update',
  templateUrl: './payroll-preference-update.component.html',
})
export class PayrollPreferenceUpdateComponent implements OnInit {
  isSaving = false;
  parties: IParty[] = [];
  deductiontypes: IDeductionType[] = [];
  paymentmethodtypes: IPaymentMethodType[] = [];
  periodtypes: IPeriodType[] = [];

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    sequenceNo: [],
    percentage: [],
    flatAmount: [],
    accountNumber: [],
    bankName: [],
    ifscCode: [],
    branch: [],
    employee: [],
    deductionType: [],
    paymentMethodType: [],
    periodType: [],
  });

  constructor(
    protected payrollPreferenceService: PayrollPreferenceService,
    protected partyService: PartyService,
    protected deductionTypeService: DeductionTypeService,
    protected paymentMethodTypeService: PaymentMethodTypeService,
    protected periodTypeService: PeriodTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payrollPreference }) => {
      if (!payrollPreference.id) {
        const today = moment().startOf('day');
        payrollPreference.fromDate = today;
        payrollPreference.thruDate = today;
      }

      this.updateForm(payrollPreference);

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.deductionTypeService.query().subscribe((res: HttpResponse<IDeductionType[]>) => (this.deductiontypes = res.body || []));

      this.paymentMethodTypeService
        .query()
        .subscribe((res: HttpResponse<IPaymentMethodType[]>) => (this.paymentmethodtypes = res.body || []));

      this.periodTypeService.query().subscribe((res: HttpResponse<IPeriodType[]>) => (this.periodtypes = res.body || []));
    });
  }

  updateForm(payrollPreference: IPayrollPreference): void {
    this.editForm.patchValue({
      id: payrollPreference.id,
      fromDate: payrollPreference.fromDate ? payrollPreference.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: payrollPreference.thruDate ? payrollPreference.thruDate.format(DATE_TIME_FORMAT) : null,
      sequenceNo: payrollPreference.sequenceNo,
      percentage: payrollPreference.percentage,
      flatAmount: payrollPreference.flatAmount,
      accountNumber: payrollPreference.accountNumber,
      bankName: payrollPreference.bankName,
      ifscCode: payrollPreference.ifscCode,
      branch: payrollPreference.branch,
      employee: payrollPreference.employee,
      deductionType: payrollPreference.deductionType,
      paymentMethodType: payrollPreference.paymentMethodType,
      periodType: payrollPreference.periodType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const payrollPreference = this.createFromForm();
    if (payrollPreference.id !== undefined) {
      this.subscribeToSaveResponse(this.payrollPreferenceService.update(payrollPreference));
    } else {
      this.subscribeToSaveResponse(this.payrollPreferenceService.create(payrollPreference));
    }
  }

  private createFromForm(): IPayrollPreference {
    return {
      ...new PayrollPreference(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? moment(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      percentage: this.editForm.get(['percentage'])!.value,
      flatAmount: this.editForm.get(['flatAmount'])!.value,
      accountNumber: this.editForm.get(['accountNumber'])!.value,
      bankName: this.editForm.get(['bankName'])!.value,
      ifscCode: this.editForm.get(['ifscCode'])!.value,
      branch: this.editForm.get(['branch'])!.value,
      employee: this.editForm.get(['employee'])!.value,
      deductionType: this.editForm.get(['deductionType'])!.value,
      paymentMethodType: this.editForm.get(['paymentMethodType'])!.value,
      periodType: this.editForm.get(['periodType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayrollPreference>>): void {
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
