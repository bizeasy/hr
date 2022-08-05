import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductStoreFacility, ProductStoreFacility } from 'app/shared/model/product-store-facility.model';
import { ProductStoreFacilityService } from './product-store-facility.service';
import { ProductStoreFacilityComponent } from './product-store-facility.component';
import { ProductStoreFacilityDetailComponent } from './product-store-facility-detail.component';
import { ProductStoreFacilityUpdateComponent } from './product-store-facility-update.component';

@Injectable({ providedIn: 'root' })
export class ProductStoreFacilityResolve implements Resolve<IProductStoreFacility> {
  constructor(private service: ProductStoreFacilityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductStoreFacility> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productStoreFacility: HttpResponse<ProductStoreFacility>) => {
          if (productStoreFacility.body) {
            return of(productStoreFacility.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductStoreFacility());
  }
}

export const productStoreFacilityRoute: Routes = [
  {
    path: '',
    component: ProductStoreFacilityComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.productStoreFacility.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductStoreFacilityDetailComponent,
    resolve: {
      productStoreFacility: ProductStoreFacilityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productStoreFacility.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductStoreFacilityUpdateComponent,
    resolve: {
      productStoreFacility: ProductStoreFacilityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productStoreFacility.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductStoreFacilityUpdateComponent,
    resolve: {
      productStoreFacility: ProductStoreFacilityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productStoreFacility.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
