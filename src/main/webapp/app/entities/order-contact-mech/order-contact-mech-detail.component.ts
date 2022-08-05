import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderContactMech } from 'app/shared/model/order-contact-mech.model';

@Component({
  selector: 'sys-order-contact-mech-detail',
  templateUrl: './order-contact-mech-detail.component.html',
})
export class OrderContactMechDetailComponent implements OnInit {
  orderContactMech: IOrderContactMech | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderContactMech }) => (this.orderContactMech = orderContactMech));
  }

  previousState(): void {
    window.history.back();
  }
}
