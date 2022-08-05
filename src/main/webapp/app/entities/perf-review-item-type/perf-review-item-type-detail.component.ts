import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPerfReviewItemType } from 'app/shared/model/perf-review-item-type.model';

@Component({
  selector: 'sys-perf-review-item-type-detail',
  templateUrl: './perf-review-item-type-detail.component.html',
})
export class PerfReviewItemTypeDetailComponent implements OnInit {
  perfReviewItemType: IPerfReviewItemType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfReviewItemType }) => (this.perfReviewItemType = perfReviewItemType));
  }

  previousState(): void {
    window.history.back();
  }
}
