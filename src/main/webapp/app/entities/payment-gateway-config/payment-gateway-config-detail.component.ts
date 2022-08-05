import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentGatewayConfig } from 'app/shared/model/payment-gateway-config.model';

@Component({
  selector: 'sys-payment-gateway-config-detail',
  templateUrl: './payment-gateway-config-detail.component.html',
})
export class PaymentGatewayConfigDetailComponent implements OnInit {
  paymentGatewayConfig: IPaymentGatewayConfig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentGatewayConfig }) => (this.paymentGatewayConfig = paymentGatewayConfig));
  }

  previousState(): void {
    window.history.back();
  }
}
