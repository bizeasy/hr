import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmplPositionReportingStruct } from 'app/shared/model/empl-position-reporting-struct.model';

@Component({
  selector: 'sys-empl-position-reporting-struct-detail',
  templateUrl: './empl-position-reporting-struct-detail.component.html',
})
export class EmplPositionReportingStructDetailComponent implements OnInit {
  emplPositionReportingStruct: IEmplPositionReportingStruct | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ emplPositionReportingStruct }) => (this.emplPositionReportingStruct = emplPositionReportingStruct)
    );
  }

  previousState(): void {
    window.history.back();
  }
}
