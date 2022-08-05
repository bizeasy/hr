import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductStoreType, ProductStoreType } from 'app/shared/model/product-store-type.model';
import { ProductStoreTypeService } from './product-store-type.service';
import { ProductStoreTypeComponent } from './product-store-type.component';
import { ProductStoreTypeDetailComponent } from './product-store-type-detail.component';
import { ProductStoreTypeUpdateComponent } from './product-store-type-update.component';

@Injectable({ providedIn: 'root' })
export class ProductStoreTypeResolve implements Resolve<IProductStoreType> {
  constructor(private service: ProductStoreTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductStoreType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productStoreType: HttpResponse<ProductStoreType>) => {
          if (productStoreType.body) {
            return of(productStoreType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductStoreType());
  }
}

export const productStoreTypeRoute: Routes = [
  {
    path: '',
    component: ProductStoreTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productStoreType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductStoreTypeDetailComponent,
    resolve: {
      productStoreType: ProductStoreTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productStoreType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductStoreTypeUpdateComponent,
    resolve: {
      productStoreType: ProductStoreTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productStoreType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductStoreTypeUpdateComponent,
    resolve: {
      productStoreType: ProductStoreTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productStoreType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
