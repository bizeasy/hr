import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkEffortPurpose } from 'app/shared/model/work-effort-purpose.model';

@Component({
  selector: 'sys-work-effort-purpose-detail',
  templateUrl: './work-effort-purpose-detail.component.html',
})
export class WorkEffortPurposeDetailComponent implements OnInit {
  workEffortPurpose: IWorkEffortPurpose | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffortPurpose }) => (this.workEffortPurpose = workEffortPurpose));
  }

  previousState(): void {
    window.history.back();
  }
}
