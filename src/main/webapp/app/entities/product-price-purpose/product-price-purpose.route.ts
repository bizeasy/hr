import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductPricePurpose, ProductPricePurpose } from 'app/shared/model/product-price-purpose.model';
import { ProductPricePurposeService } from './product-price-purpose.service';
import { ProductPricePurposeComponent } from './product-price-purpose.component';
import { ProductPricePurposeDetailComponent } from './product-price-purpose-detail.component';
import { ProductPricePurposeUpdateComponent } from './product-price-purpose-update.component';

@Injectable({ providedIn: 'root' })
export class ProductPricePurposeResolve implements Resolve<IProductPricePurpose> {
  constructor(private service: ProductPricePurposeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductPricePurpose> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productPricePurpose: HttpResponse<ProductPricePurpose>) => {
          if (productPricePurpose.body) {
            return of(productPricePurpose.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductPricePurpose());
  }
}

export const productPricePurposeRoute: Routes = [
  {
    path: '',
    component: ProductPricePurposeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productPricePurpose.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductPricePurposeDetailComponent,
    resolve: {
      productPricePurpose: ProductPricePurposeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productPricePurpose.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductPricePurposeUpdateComponent,
    resolve: {
      productPricePurpose: ProductPricePurposeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productPricePurpose.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductPricePurposeUpdateComponent,
    resolve: {
      productPricePurpose: ProductPricePurposeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productPricePurpose.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
