import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkEffortAssocType } from 'app/shared/model/work-effort-assoc-type.model';

@Component({
  selector: 'sys-work-effort-assoc-type-detail',
  templateUrl: './work-effort-assoc-type-detail.component.html',
})
export class WorkEffortAssocTypeDetailComponent implements OnInit {
  workEffortAssocType: IWorkEffortAssocType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffortAssocType }) => (this.workEffortAssocType = workEffortAssocType));
  }

  previousState(): void {
    window.history.back();
  }
}
