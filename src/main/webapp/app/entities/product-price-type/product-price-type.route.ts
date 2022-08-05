import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductPriceType, ProductPriceType } from 'app/shared/model/product-price-type.model';
import { ProductPriceTypeService } from './product-price-type.service';
import { ProductPriceTypeComponent } from './product-price-type.component';
import { ProductPriceTypeDetailComponent } from './product-price-type-detail.component';
import { ProductPriceTypeUpdateComponent } from './product-price-type-update.component';

@Injectable({ providedIn: 'root' })
export class ProductPriceTypeResolve implements Resolve<IProductPriceType> {
  constructor(private service: ProductPriceTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductPriceType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productPriceType: HttpResponse<ProductPriceType>) => {
          if (productPriceType.body) {
            return of(productPriceType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductPriceType());
  }
}

export const productPriceTypeRoute: Routes = [
  {
    path: '',
    component: ProductPriceTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productPriceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductPriceTypeDetailComponent,
    resolve: {
      productPriceType: ProductPriceTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productPriceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductPriceTypeUpdateComponent,
    resolve: {
      productPriceType: ProductPriceTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productPriceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductPriceTypeUpdateComponent,
    resolve: {
      productPriceType: ProductPriceTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productPriceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
