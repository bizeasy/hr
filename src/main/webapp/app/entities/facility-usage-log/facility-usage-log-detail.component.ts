import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFacilityUsageLog } from 'app/shared/model/facility-usage-log.model';

@Component({
  selector: 'sys-facility-usage-log-detail',
  templateUrl: './facility-usage-log-detail.component.html',
})
export class FacilityUsageLogDetailComponent implements OnInit {
  facilityUsageLog: IFacilityUsageLog | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facilityUsageLog }) => (this.facilityUsageLog = facilityUsageLog));
  }

  previousState(): void {
    window.history.back();
  }
}
