import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkEffortInventoryAssign } from 'app/shared/model/work-effort-inventory-assign.model';

@Component({
  selector: 'sys-work-effort-inventory-assign-detail',
  templateUrl: './work-effort-inventory-assign-detail.component.html',
})
export class WorkEffortInventoryAssignDetailComponent implements OnInit {
  workEffortInventoryAssign: IWorkEffortInventoryAssign | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffortInventoryAssign }) => (this.workEffortInventoryAssign = workEffortInventoryAssign));
  }

  previousState(): void {
    window.history.back();
  }
}
