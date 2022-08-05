import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductCategoryType, ProductCategoryType } from 'app/shared/model/product-category-type.model';
import { ProductCategoryTypeService } from './product-category-type.service';
import { ProductCategoryTypeComponent } from './product-category-type.component';
import { ProductCategoryTypeDetailComponent } from './product-category-type-detail.component';
import { ProductCategoryTypeUpdateComponent } from './product-category-type-update.component';

@Injectable({ providedIn: 'root' })
export class ProductCategoryTypeResolve implements Resolve<IProductCategoryType> {
  constructor(private service: ProductCategoryTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductCategoryType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productCategoryType: HttpResponse<ProductCategoryType>) => {
          if (productCategoryType.body) {
            return of(productCategoryType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductCategoryType());
  }
}

export const productCategoryTypeRoute: Routes = [
  {
    path: '',
    component: ProductCategoryTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productCategoryType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductCategoryTypeDetailComponent,
    resolve: {
      productCategoryType: ProductCategoryTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productCategoryType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductCategoryTypeUpdateComponent,
    resolve: {
      productCategoryType: ProductCategoryTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productCategoryType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductCategoryTypeUpdateComponent,
    resolve: {
      productCategoryType: ProductCategoryTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productCategoryType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
