import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrderType, OrderType } from 'app/shared/model/order-type.model';
import { OrderTypeService } from './order-type.service';
import { OrderTypeComponent } from './order-type.component';
import { OrderTypeDetailComponent } from './order-type-detail.component';
import { OrderTypeUpdateComponent } from './order-type-update.component';

@Injectable({ providedIn: 'root' })
export class OrderTypeResolve implements Resolve<IOrderType> {
  constructor(private service: OrderTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((orderType: HttpResponse<OrderType>) => {
          if (orderType.body) {
            return of(orderType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderType());
  }
}

export const orderTypeRoute: Routes = [
  {
    path: '',
    component: OrderTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrderTypeDetailComponent,
    resolve: {
      orderType: OrderTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrderTypeUpdateComponent,
    resolve: {
      orderType: OrderTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrderTypeUpdateComponent,
    resolve: {
      orderType: OrderTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
