import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProductCategory } from 'app/shared/model/product-category.model';

@Component({
  selector: 'sys-product-category-detail',
  templateUrl: './product-category-detail.component.html',
})
export class ProductCategoryDetailComponent implements OnInit {
  productCategory: IProductCategory | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productCategory }) => (this.productCategory = productCategory));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
