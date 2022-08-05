import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReasonType } from 'app/shared/model/reason-type.model';

@Component({
  selector: 'sys-reason-type-detail',
  templateUrl: './reason-type-detail.component.html',
})
export class ReasonTypeDetailComponent implements OnInit {
  reasonType: IReasonType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reasonType }) => (this.reasonType = reasonType));
  }

  previousState(): void {
    window.history.back();
  }
}
