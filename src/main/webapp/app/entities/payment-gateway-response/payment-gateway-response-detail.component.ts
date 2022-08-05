import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentGatewayResponse } from 'app/shared/model/payment-gateway-response.model';

@Component({
  selector: 'sys-payment-gateway-response-detail',
  templateUrl: './payment-gateway-response-detail.component.html',
})
export class PaymentGatewayResponseDetailComponent implements OnInit {
  paymentGatewayResponse: IPaymentGatewayResponse | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentGatewayResponse }) => (this.paymentGatewayResponse = paymentGatewayResponse));
  }

  previousState(): void {
    window.history.back();
  }
}
