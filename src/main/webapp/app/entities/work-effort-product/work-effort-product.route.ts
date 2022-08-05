import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorkEffortProduct, WorkEffortProduct } from 'app/shared/model/work-effort-product.model';
import { WorkEffortProductService } from './work-effort-product.service';
import { WorkEffortProductComponent } from './work-effort-product.component';
import { WorkEffortProductDetailComponent } from './work-effort-product-detail.component';
import { WorkEffortProductUpdateComponent } from './work-effort-product-update.component';

@Injectable({ providedIn: 'root' })
export class WorkEffortProductResolve implements Resolve<IWorkEffortProduct> {
  constructor(private service: WorkEffortProductService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkEffortProduct> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((workEffortProduct: HttpResponse<WorkEffortProduct>) => {
          if (workEffortProduct.body) {
            return of(workEffortProduct.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkEffortProduct());
  }
}

export const workEffortProductRoute: Routes = [
  {
    path: '',
    component: WorkEffortProductComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.workEffortProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkEffortProductDetailComponent,
    resolve: {
      workEffortProduct: WorkEffortProductResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkEffortProductUpdateComponent,
    resolve: {
      workEffortProduct: WorkEffortProductResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkEffortProductUpdateComponent,
    resolve: {
      workEffortProduct: WorkEffortProductResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
