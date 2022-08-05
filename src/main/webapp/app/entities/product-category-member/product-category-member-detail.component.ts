import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductCategoryMember } from 'app/shared/model/product-category-member.model';

@Component({
  selector: 'sys-product-category-member-detail',
  templateUrl: './product-category-member-detail.component.html',
})
export class ProductCategoryMemberDetailComponent implements OnInit {
  productCategoryMember: IProductCategoryMember | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productCategoryMember }) => (this.productCategoryMember = productCategoryMember));
  }

  previousState(): void {
    window.history.back();
  }
}
