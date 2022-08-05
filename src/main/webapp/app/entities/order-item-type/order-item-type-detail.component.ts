import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderItemType } from 'app/shared/model/order-item-type.model';

@Component({
  selector: 'sys-order-item-type-detail',
  templateUrl: './order-item-type-detail.component.html',
})
export class OrderItemTypeDetailComponent implements OnInit {
  orderItemType: IOrderItemType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderItemType }) => (this.orderItemType = orderItemType));
  }

  previousState(): void {
    window.history.back();
  }
}
