import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkEffortType } from 'app/shared/model/work-effort-type.model';

@Component({
  selector: 'sys-work-effort-type-detail',
  templateUrl: './work-effort-type-detail.component.html',
})
export class WorkEffortTypeDetailComponent implements OnInit {
  workEffortType: IWorkEffortType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffortType }) => (this.workEffortType = workEffortType));
  }

  previousState(): void {
    window.history.back();
  }
}
