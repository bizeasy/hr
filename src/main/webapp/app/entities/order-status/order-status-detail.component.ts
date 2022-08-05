import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderStatus } from 'app/shared/model/order-status.model';

@Component({
  selector: 'sys-order-status-detail',
  templateUrl: './order-status-detail.component.html',
})
export class OrderStatusDetailComponent implements OnInit {
  orderStatus: IOrderStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderStatus }) => (this.orderStatus = orderStatus));
  }

  previousState(): void {
    window.history.back();
  }
}
