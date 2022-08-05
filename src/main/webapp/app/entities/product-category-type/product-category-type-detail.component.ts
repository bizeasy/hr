import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductCategoryType } from 'app/shared/model/product-category-type.model';

@Component({
  selector: 'sys-product-category-type-detail',
  templateUrl: './product-category-type-detail.component.html',
})
export class ProductCategoryTypeDetailComponent implements OnInit {
  productCategoryType: IProductCategoryType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productCategoryType }) => (this.productCategoryType = productCategoryType));
  }

  previousState(): void {
    window.history.back();
  }
}
