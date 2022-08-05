import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmplPosition } from 'app/shared/model/empl-position.model';

@Component({
  selector: 'sys-empl-position-detail',
  templateUrl: './empl-position-detail.component.html',
})
export class EmplPositionDetailComponent implements OnInit {
  emplPosition: IEmplPosition | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplPosition }) => (this.emplPosition = emplPosition));
  }

  previousState(): void {
    window.history.back();
  }
}
