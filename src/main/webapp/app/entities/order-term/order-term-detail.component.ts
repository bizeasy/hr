import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderTerm } from 'app/shared/model/order-term.model';

@Component({
  selector: 'sys-order-term-detail',
  templateUrl: './order-term-detail.component.html',
})
export class OrderTermDetailComponent implements OnInit {
  orderTerm: IOrderTerm | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderTerm }) => (this.orderTerm = orderTerm));
  }

  previousState(): void {
    window.history.back();
  }
}
