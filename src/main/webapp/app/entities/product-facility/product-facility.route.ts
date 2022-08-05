import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductFacility, ProductFacility } from 'app/shared/model/product-facility.model';
import { ProductFacilityService } from './product-facility.service';
import { ProductFacilityComponent } from './product-facility.component';
import { ProductFacilityDetailComponent } from './product-facility-detail.component';
import { ProductFacilityUpdateComponent } from './product-facility-update.component';

@Injectable({ providedIn: 'root' })
export class ProductFacilityResolve implements Resolve<IProductFacility> {
  constructor(private service: ProductFacilityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductFacility> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productFacility: HttpResponse<ProductFacility>) => {
          if (productFacility.body) {
            return of(productFacility.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductFacility());
  }
}

export const productFacilityRoute: Routes = [
  {
    path: '',
    component: ProductFacilityComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.productFacility.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductFacilityDetailComponent,
    resolve: {
      productFacility: ProductFacilityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productFacility.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductFacilityUpdateComponent,
    resolve: {
      productFacility: ProductFacilityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productFacility.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductFacilityUpdateComponent,
    resolve: {
      productFacility: ProductFacilityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productFacility.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
