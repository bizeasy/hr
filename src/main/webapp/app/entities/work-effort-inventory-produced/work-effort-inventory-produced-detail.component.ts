import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkEffortInventoryProduced } from 'app/shared/model/work-effort-inventory-produced.model';

@Component({
  selector: 'sys-work-effort-inventory-produced-detail',
  templateUrl: './work-effort-inventory-produced-detail.component.html',
})
export class WorkEffortInventoryProducedDetailComponent implements OnInit {
  workEffortInventoryProduced: IWorkEffortInventoryProduced | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ workEffortInventoryProduced }) => (this.workEffortInventoryProduced = workEffortInventoryProduced)
    );
  }

  previousState(): void {
    window.history.back();
  }
}
