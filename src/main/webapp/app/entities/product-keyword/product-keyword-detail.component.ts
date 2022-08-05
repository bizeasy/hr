import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductKeyword } from 'app/shared/model/product-keyword.model';

@Component({
  selector: 'sys-product-keyword-detail',
  templateUrl: './product-keyword-detail.component.html',
})
export class ProductKeywordDetailComponent implements OnInit {
  productKeyword: IProductKeyword | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productKeyword }) => (this.productKeyword = productKeyword));
  }

  previousState(): void {
    window.history.back();
  }
}
