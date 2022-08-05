import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentApplication } from 'app/shared/model/payment-application.model';

@Component({
  selector: 'sys-payment-application-detail',
  templateUrl: './payment-application-detail.component.html',
})
export class PaymentApplicationDetailComponent implements OnInit {
  paymentApplication: IPaymentApplication | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentApplication }) => (this.paymentApplication = paymentApplication));
  }

  previousState(): void {
    window.history.back();
  }
}
