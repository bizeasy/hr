import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductFacility } from 'app/shared/model/product-facility.model';

@Component({
  selector: 'sys-product-facility-detail',
  templateUrl: './product-facility-detail.component.html',
})
export class ProductFacilityDetailComponent implements OnInit {
  productFacility: IProductFacility | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productFacility }) => (this.productFacility = productFacility));
  }

  previousState(): void {
    window.history.back();
  }
}
