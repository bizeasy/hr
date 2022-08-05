import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPerfReview } from 'app/shared/model/perf-review.model';

@Component({
  selector: 'sys-perf-review-detail',
  templateUrl: './perf-review-detail.component.html',
})
export class PerfReviewDetailComponent implements OnInit {
  perfReview: IPerfReview | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfReview }) => (this.perfReview = perfReview));
  }

  previousState(): void {
    window.history.back();
  }
}
