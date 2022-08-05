import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductStoreProduct, ProductStoreProduct } from 'app/shared/model/product-store-product.model';
import { ProductStoreProductService } from './product-store-product.service';
import { ProductStoreProductComponent } from './product-store-product.component';
import { ProductStoreProductDetailComponent } from './product-store-product-detail.component';
import { ProductStoreProductUpdateComponent } from './product-store-product-update.component';

@Injectable({ providedIn: 'root' })
export class ProductStoreProductResolve implements Resolve<IProductStoreProduct> {
  constructor(private service: ProductStoreProductService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductStoreProduct> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productStoreProduct: HttpResponse<ProductStoreProduct>) => {
          if (productStoreProduct.body) {
            return of(productStoreProduct.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductStoreProduct());
  }
}

export const productStoreProductRoute: Routes = [
  {
    path: '',
    component: ProductStoreProductComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productStoreProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductStoreProductDetailComponent,
    resolve: {
      productStoreProduct: ProductStoreProductResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productStoreProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductStoreProductUpdateComponent,
    resolve: {
      productStoreProduct: ProductStoreProductResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productStoreProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductStoreProductUpdateComponent,
    resolve: {
      productStoreProduct: ProductStoreProductResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productStoreProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
