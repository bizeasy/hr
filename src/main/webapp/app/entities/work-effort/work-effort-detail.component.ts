import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkEffort } from 'app/shared/model/work-effort.model';

@Component({
  selector: 'sys-work-effort-detail',
  templateUrl: './work-effort-detail.component.html',
})
export class WorkEffortDetailComponent implements OnInit {
  workEffort: IWorkEffort | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffort }) => (this.workEffort = workEffort));
  }

  previousState(): void {
    window.history.back();
  }
}
