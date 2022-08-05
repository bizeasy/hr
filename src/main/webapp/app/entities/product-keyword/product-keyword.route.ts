import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductKeyword, ProductKeyword } from 'app/shared/model/product-keyword.model';
import { ProductKeywordService } from './product-keyword.service';
import { ProductKeywordComponent } from './product-keyword.component';
import { ProductKeywordDetailComponent } from './product-keyword-detail.component';
import { ProductKeywordUpdateComponent } from './product-keyword-update.component';

@Injectable({ providedIn: 'root' })
export class ProductKeywordResolve implements Resolve<IProductKeyword> {
  constructor(private service: ProductKeywordService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductKeyword> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productKeyword: HttpResponse<ProductKeyword>) => {
          if (productKeyword.body) {
            return of(productKeyword.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductKeyword());
  }
}

export const productKeywordRoute: Routes = [
  {
    path: '',
    component: ProductKeywordComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productKeyword.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductKeywordDetailComponent,
    resolve: {
      productKeyword: ProductKeywordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productKeyword.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductKeywordUpdateComponent,
    resolve: {
      productKeyword: ProductKeywordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productKeyword.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductKeywordUpdateComponent,
    resolve: {
      productKeyword: ProductKeywordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productKeyword.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
