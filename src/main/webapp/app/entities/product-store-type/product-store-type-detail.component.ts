import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductStoreType } from 'app/shared/model/product-store-type.model';

@Component({
  selector: 'sys-product-store-type-detail',
  templateUrl: './product-store-type-detail.component.html',
})
export class ProductStoreTypeDetailComponent implements OnInit {
  productStoreType: IProductStoreType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productStoreType }) => (this.productStoreType = productStoreType));
  }

  previousState(): void {
    window.history.back();
  }
}
