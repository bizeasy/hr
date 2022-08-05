import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkEffortStatus } from 'app/shared/model/work-effort-status.model';

@Component({
  selector: 'sys-work-effort-status-detail',
  templateUrl: './work-effort-status-detail.component.html',
})
export class WorkEffortStatusDetailComponent implements OnInit {
  workEffortStatus: IWorkEffortStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffortStatus }) => (this.workEffortStatus = workEffortStatus));
  }

  previousState(): void {
    window.history.back();
  }
}
