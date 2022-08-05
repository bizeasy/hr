import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmplPositionGroup } from 'app/shared/model/empl-position-group.model';

@Component({
  selector: 'sys-empl-position-group-detail',
  templateUrl: './empl-position-group-detail.component.html',
})
export class EmplPositionGroupDetailComponent implements OnInit {
  emplPositionGroup: IEmplPositionGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplPositionGroup }) => (this.emplPositionGroup = emplPositionGroup));
  }

  previousState(): void {
    window.history.back();
  }
}
