import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderTermType } from 'app/shared/model/order-term-type.model';

@Component({
  selector: 'sys-order-term-type-detail',
  templateUrl: './order-term-type-detail.component.html',
})
export class OrderTermTypeDetailComponent implements OnInit {
  orderTermType: IOrderTermType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderTermType }) => (this.orderTermType = orderTermType));
  }

  previousState(): void {
    window.history.back();
  }
}
