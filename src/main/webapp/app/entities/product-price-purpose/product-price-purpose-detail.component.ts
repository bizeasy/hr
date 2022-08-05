import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductPricePurpose } from 'app/shared/model/product-price-purpose.model';

@Component({
  selector: 'sys-product-price-purpose-detail',
  templateUrl: './product-price-purpose-detail.component.html',
})
export class ProductPricePurposeDetailComponent implements OnInit {
  productPricePurpose: IProductPricePurpose | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productPricePurpose }) => (this.productPricePurpose = productPricePurpose));
  }

  previousState(): void {
    window.history.back();
  }
}
