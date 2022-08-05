import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductPrice, ProductPrice } from 'app/shared/model/product-price.model';
import { ProductPriceService } from './product-price.service';
import { ProductPriceComponent } from './product-price.component';
import { ProductPriceDetailComponent } from './product-price-detail.component';
import { ProductPriceUpdateComponent } from './product-price-update.component';

@Injectable({ providedIn: 'root' })
export class ProductPriceResolve implements Resolve<IProductPrice> {
  constructor(private service: ProductPriceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductPrice> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productPrice: HttpResponse<ProductPrice>) => {
          if (productPrice.body) {
            return of(productPrice.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductPrice());
  }
}

export const productPriceRoute: Routes = [
  {
    path: '',
    component: ProductPriceComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.productPrice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductPriceDetailComponent,
    resolve: {
      productPrice: ProductPriceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productPrice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductPriceUpdateComponent,
    resolve: {
      productPrice: ProductPriceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productPrice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductPriceUpdateComponent,
    resolve: {
      productPrice: ProductPriceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productPrice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
