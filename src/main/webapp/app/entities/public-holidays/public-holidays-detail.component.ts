import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPublicHolidays } from 'app/shared/model/public-holidays.model';

@Component({
  selector: 'sys-public-holidays-detail',
  templateUrl: './public-holidays-detail.component.html',
})
export class PublicHolidaysDetailComponent implements OnInit {
  publicHolidays: IPublicHolidays | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ publicHolidays }) => (this.publicHolidays = publicHolidays));
  }

  previousState(): void {
    window.history.back();
  }
}
