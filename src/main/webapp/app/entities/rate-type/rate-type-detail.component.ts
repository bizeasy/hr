import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRateType } from 'app/shared/model/rate-type.model';

@Component({
  selector: 'sys-rate-type-detail',
  templateUrl: './rate-type-detail.component.html',
})
export class RateTypeDetailComponent implements OnInit {
  rateType: IRateType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rateType }) => (this.rateType = rateType));
  }

  previousState(): void {
    window.history.back();
  }
}
