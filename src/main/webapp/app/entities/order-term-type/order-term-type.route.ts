import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrderTermType, OrderTermType } from 'app/shared/model/order-term-type.model';
import { OrderTermTypeService } from './order-term-type.service';
import { OrderTermTypeComponent } from './order-term-type.component';
import { OrderTermTypeDetailComponent } from './order-term-type-detail.component';
import { OrderTermTypeUpdateComponent } from './order-term-type-update.component';

@Injectable({ providedIn: 'root' })
export class OrderTermTypeResolve implements Resolve<IOrderTermType> {
  constructor(private service: OrderTermTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderTermType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((orderTermType: HttpResponse<OrderTermType>) => {
          if (orderTermType.body) {
            return of(orderTermType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderTermType());
  }
}

export const orderTermTypeRoute: Routes = [
  {
    path: '',
    component: OrderTermTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderTermType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrderTermTypeDetailComponent,
    resolve: {
      orderTermType: OrderTermTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderTermType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrderTermTypeUpdateComponent,
    resolve: {
      orderTermType: OrderTermTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderTermType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrderTermTypeUpdateComponent,
    resolve: {
      orderTermType: OrderTermTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderTermType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
