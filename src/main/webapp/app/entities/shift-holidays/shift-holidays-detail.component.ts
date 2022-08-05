import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShiftHolidays } from 'app/shared/model/shift-holidays.model';

@Component({
  selector: 'sys-shift-holidays-detail',
  templateUrl: './shift-holidays-detail.component.html',
})
export class ShiftHolidaysDetailComponent implements OnInit {
  shiftHolidays: IShiftHolidays | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shiftHolidays }) => (this.shiftHolidays = shiftHolidays));
  }

  previousState(): void {
    window.history.back();
  }
}
