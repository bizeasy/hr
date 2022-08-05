import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPerfRatingType } from 'app/shared/model/perf-rating-type.model';

@Component({
  selector: 'sys-perf-rating-type-detail',
  templateUrl: './perf-rating-type-detail.component.html',
})
export class PerfRatingTypeDetailComponent implements OnInit {
  perfRatingType: IPerfRatingType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfRatingType }) => (this.perfRatingType = perfRatingType));
  }

  previousState(): void {
    window.history.back();
  }
}
