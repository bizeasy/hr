import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISupplierProduct, SupplierProduct } from 'app/shared/model/supplier-product.model';
import { SupplierProductService } from './supplier-product.service';
import { SupplierProductComponent } from './supplier-product.component';
import { SupplierProductDetailComponent } from './supplier-product-detail.component';
import { SupplierProductUpdateComponent } from './supplier-product-update.component';

@Injectable({ providedIn: 'root' })
export class SupplierProductResolve implements Resolve<ISupplierProduct> {
  constructor(private service: SupplierProductService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISupplierProduct> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((supplierProduct: HttpResponse<SupplierProduct>) => {
          if (supplierProduct.body) {
            return of(supplierProduct.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SupplierProduct());
  }
}

export const supplierProductRoute: Routes = [
  {
    path: '',
    component: SupplierProductComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.supplierProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SupplierProductDetailComponent,
    resolve: {
      supplierProduct: SupplierProductResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.supplierProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SupplierProductUpdateComponent,
    resolve: {
      supplierProduct: SupplierProductResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.supplierProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SupplierProductUpdateComponent,
    resolve: {
      supplierProduct: SupplierProductResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.supplierProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
