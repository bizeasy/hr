import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeriodType } from 'app/shared/model/period-type.model';

@Component({
  selector: 'sys-period-type-detail',
  templateUrl: './period-type-detail.component.html',
})
export class PeriodTypeDetailComponent implements OnInit {
  periodType: IPeriodType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ periodType }) => (this.periodType = periodType));
  }

  previousState(): void {
    window.history.back();
  }
}
