import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkEffortAssoc } from 'app/shared/model/work-effort-assoc.model';

@Component({
  selector: 'sys-work-effort-assoc-detail',
  templateUrl: './work-effort-assoc-detail.component.html',
})
export class WorkEffortAssocDetailComponent implements OnInit {
  workEffortAssoc: IWorkEffortAssoc | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffortAssoc }) => (this.workEffortAssoc = workEffortAssoc));
  }

  previousState(): void {
    window.history.back();
  }
}
