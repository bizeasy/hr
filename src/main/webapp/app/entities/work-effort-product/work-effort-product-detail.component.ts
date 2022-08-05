import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkEffortProduct } from 'app/shared/model/work-effort-product.model';

@Component({
  selector: 'sys-work-effort-product-detail',
  templateUrl: './work-effort-product-detail.component.html',
})
export class WorkEffortProductDetailComponent implements OnInit {
  workEffortProduct: IWorkEffortProduct | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffortProduct }) => (this.workEffortProduct = workEffortProduct));
  }

  previousState(): void {
    window.history.back();
  }
}
