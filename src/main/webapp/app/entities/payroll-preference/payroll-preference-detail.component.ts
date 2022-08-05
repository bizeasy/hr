import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPayrollPreference } from 'app/shared/model/payroll-preference.model';

@Component({
  selector: 'sys-payroll-preference-detail',
  templateUrl: './payroll-preference-detail.component.html',
})
export class PayrollPreferenceDetailComponent implements OnInit {
  payrollPreference: IPayrollPreference | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payrollPreference }) => (this.payrollPreference = payrollPreference));
  }

  previousState(): void {
    window.history.back();
  }
}
