import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomTimePeriod } from 'app/shared/model/custom-time-period.model';

@Component({
  selector: 'sys-custom-time-period-detail',
  templateUrl: './custom-time-period-detail.component.html',
})
export class CustomTimePeriodDetailComponent implements OnInit {
  customTimePeriod: ICustomTimePeriod | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customTimePeriod }) => (this.customTimePeriod = customTimePeriod));
  }

  previousState(): void {
    window.history.back();
  }
}
