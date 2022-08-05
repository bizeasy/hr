import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductStoreFacility } from 'app/shared/model/product-store-facility.model';

@Component({
  selector: 'sys-product-store-facility-detail',
  templateUrl: './product-store-facility-detail.component.html',
})
export class ProductStoreFacilityDetailComponent implements OnInit {
  productStoreFacility: IProductStoreFacility | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productStoreFacility }) => (this.productStoreFacility = productStoreFacility));
  }

  previousState(): void {
    window.history.back();
  }
}
