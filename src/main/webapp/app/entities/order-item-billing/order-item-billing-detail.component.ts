import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderItemBilling } from 'app/shared/model/order-item-billing.model';

@Component({
  selector: 'sys-order-item-billing-detail',
  templateUrl: './order-item-billing-detail.component.html',
})
export class OrderItemBillingDetailComponent implements OnInit {
  orderItemBilling: IOrderItemBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderItemBilling }) => (this.orderItemBilling = orderItemBilling));
  }

  previousState(): void {
    window.history.back();
  }
}
