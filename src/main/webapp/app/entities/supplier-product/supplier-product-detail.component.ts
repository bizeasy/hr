import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplierProduct } from 'app/shared/model/supplier-product.model';

@Component({
  selector: 'sys-supplier-product-detail',
  templateUrl: './supplier-product-detail.component.html',
})
export class SupplierProductDetailComponent implements OnInit {
  supplierProduct: ISupplierProduct | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supplierProduct }) => (this.supplierProduct = supplierProduct));
  }

  previousState(): void {
    window.history.back();
  }
}
