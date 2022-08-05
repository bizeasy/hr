import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPerfReviewItem } from 'app/shared/model/perf-review-item.model';

@Component({
  selector: 'sys-perf-review-item-detail',
  templateUrl: './perf-review-item-detail.component.html',
})
export class PerfReviewItemDetailComponent implements OnInit {
  perfReviewItem: IPerfReviewItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfReviewItem }) => (this.perfReviewItem = perfReviewItem));
  }

  previousState(): void {
    window.history.back();
  }
}
