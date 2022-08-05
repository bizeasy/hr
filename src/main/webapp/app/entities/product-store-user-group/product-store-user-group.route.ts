import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductStoreUserGroup, ProductStoreUserGroup } from 'app/shared/model/product-store-user-group.model';
import { ProductStoreUserGroupService } from './product-store-user-group.service';
import { ProductStoreUserGroupComponent } from './product-store-user-group.component';
import { ProductStoreUserGroupDetailComponent } from './product-store-user-group-detail.component';
import { ProductStoreUserGroupUpdateComponent } from './product-store-user-group-update.component';

@Injectable({ providedIn: 'root' })
export class ProductStoreUserGroupResolve implements Resolve<IProductStoreUserGroup> {
  constructor(private service: ProductStoreUserGroupService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductStoreUserGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productStoreUserGroup: HttpResponse<ProductStoreUserGroup>) => {
          if (productStoreUserGroup.body) {
            return of(productStoreUserGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductStoreUserGroup());
  }
}

export const productStoreUserGroupRoute: Routes = [
  {
    path: '',
    component: ProductStoreUserGroupComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.productStoreUserGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductStoreUserGroupDetailComponent,
    resolve: {
      productStoreUserGroup: ProductStoreUserGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productStoreUserGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductStoreUserGroupUpdateComponent,
    resolve: {
      productStoreUserGroup: ProductStoreUserGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productStoreUserGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductStoreUserGroupUpdateComponent,
    resolve: {
      productStoreUserGroup: ProductStoreUserGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productStoreUserGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
