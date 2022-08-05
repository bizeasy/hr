import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmplPositionTypeRate } from 'app/shared/model/empl-position-type-rate.model';

@Component({
  selector: 'sys-empl-position-type-rate-detail',
  templateUrl: './empl-position-type-rate-detail.component.html',
})
export class EmplPositionTypeRateDetailComponent implements OnInit {
  emplPositionTypeRate: IEmplPositionTypeRate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplPositionTypeRate }) => (this.emplPositionTypeRate = emplPositionTypeRate));
  }

  previousState(): void {
    window.history.back();
  }
}
