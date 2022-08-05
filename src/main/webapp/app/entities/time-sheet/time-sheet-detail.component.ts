import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITimeSheet } from 'app/shared/model/time-sheet.model';

@Component({
  selector: 'sys-time-sheet-detail',
  templateUrl: './time-sheet-detail.component.html',
})
export class TimeSheetDetailComponent implements OnInit {
  timeSheet: ITimeSheet | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ timeSheet }) => (this.timeSheet = timeSheet));
  }

  previousState(): void {
    window.history.back();
  }
}
