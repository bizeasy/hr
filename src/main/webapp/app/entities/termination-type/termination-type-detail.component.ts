import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITerminationType } from 'app/shared/model/termination-type.model';

@Component({
  selector: 'sys-termination-type-detail',
  templateUrl: './termination-type-detail.component.html',
})
export class TerminationTypeDetailComponent implements OnInit {
  terminationType: ITerminationType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ terminationType }) => (this.terminationType = terminationType));
  }

  previousState(): void {
    window.history.back();
  }
}
