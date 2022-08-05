import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductPriceType } from 'app/shared/model/product-price-type.model';

@Component({
  selector: 'sys-product-price-type-detail',
  templateUrl: './product-price-type-detail.component.html',
})
export class ProductPriceTypeDetailComponent implements OnInit {
  productPriceType: IProductPriceType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productPriceType }) => (this.productPriceType = productPriceType));
  }

  previousState(): void {
    window.history.back();
  }
}
