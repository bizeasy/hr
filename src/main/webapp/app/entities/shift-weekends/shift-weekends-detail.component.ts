import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShiftWeekends } from 'app/shared/model/shift-weekends.model';

@Component({
  selector: 'sys-shift-weekends-detail',
  templateUrl: './shift-weekends-detail.component.html',
})
export class ShiftWeekendsDetailComponent implements OnInit {
  shiftWeekends: IShiftWeekends | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shiftWeekends }) => (this.shiftWeekends = shiftWeekends));
  }

  previousState(): void {
    window.history.back();
  }
}
