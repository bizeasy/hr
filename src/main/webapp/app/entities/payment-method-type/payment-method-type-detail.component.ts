import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentMethodType } from 'app/shared/model/payment-method-type.model';

@Component({
  selector: 'sys-payment-method-type-detail',
  templateUrl: './payment-method-type-detail.component.html',
})
export class PaymentMethodTypeDetailComponent implements OnInit {
  paymentMethodType: IPaymentMethodType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentMethodType }) => (this.paymentMethodType = paymentMethodType));
  }

  previousState(): void {
    window.history.back();
  }
}
