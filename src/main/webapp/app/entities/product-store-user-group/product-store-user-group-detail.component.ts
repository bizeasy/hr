import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductStoreUserGroup } from 'app/shared/model/product-store-user-group.model';

@Component({
  selector: 'sys-product-store-user-group-detail',
  templateUrl: './product-store-user-group-detail.component.html',
})
export class ProductStoreUserGroupDetailComponent implements OnInit {
  productStoreUserGroup: IProductStoreUserGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productStoreUserGroup }) => (this.productStoreUserGroup = productStoreUserGroup));
  }

  previousState(): void {
    window.history.back();
  }
}
