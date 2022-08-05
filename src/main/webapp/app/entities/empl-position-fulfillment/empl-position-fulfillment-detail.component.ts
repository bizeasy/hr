import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmplPositionFulfillment } from 'app/shared/model/empl-position-fulfillment.model';

@Component({
  selector: 'sys-empl-position-fulfillment-detail',
  templateUrl: './empl-position-fulfillment-detail.component.html',
})
export class EmplPositionFulfillmentDetailComponent implements OnInit {
  emplPositionFulfillment: IEmplPositionFulfillment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplPositionFulfillment }) => (this.emplPositionFulfillment = emplPositionFulfillment));
  }

  previousState(): void {
    window.history.back();
  }
}
