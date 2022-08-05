import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductStoreProduct } from 'app/shared/model/product-store-product.model';

@Component({
  selector: 'sys-product-store-product-detail',
  templateUrl: './product-store-product-detail.component.html',
})
export class ProductStoreProductDetailComponent implements OnInit {
  productStoreProduct: IProductStoreProduct | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productStoreProduct }) => (this.productStoreProduct = productStoreProduct));
  }

  previousState(): void {
    window.history.back();
  }
}
