import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductPrice } from 'app/shared/model/product-price.model';

@Component({
  selector: 'sys-product-price-detail',
  templateUrl: './product-price-detail.component.html',
})
export class ProductPriceDetailComponent implements OnInit {
  productPrice: IProductPrice | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productPrice }) => (this.productPrice = productPrice));
  }

  previousState(): void {
    window.history.back();
  }
}
