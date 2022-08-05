import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmplPositionType } from 'app/shared/model/empl-position-type.model';

@Component({
  selector: 'sys-empl-position-type-detail',
  templateUrl: './empl-position-type-detail.component.html',
})
export class EmplPositionTypeDetailComponent implements OnInit {
  emplPositionType: IEmplPositionType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplPositionType }) => (this.emplPositionType = emplPositionType));
  }

  previousState(): void {
    window.history.back();
  }
}
