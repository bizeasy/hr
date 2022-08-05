import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrderStatus, OrderStatus } from 'app/shared/model/order-status.model';
import { OrderStatusService } from './order-status.service';
import { OrderStatusComponent } from './order-status.component';
import { OrderStatusDetailComponent } from './order-status-detail.component';
import { OrderStatusUpdateComponent } from './order-status-update.component';

@Injectable({ providedIn: 'root' })
export class OrderStatusResolve implements Resolve<IOrderStatus> {
  constructor(private service: OrderStatusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((orderStatus: HttpResponse<OrderStatus>) => {
          if (orderStatus.body) {
            return of(orderStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderStatus());
  }
}

export const orderStatusRoute: Routes = [
  {
    path: '',
    component: OrderStatusComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.orderStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrderStatusDetailComponent,
    resolve: {
      orderStatus: OrderStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrderStatusUpdateComponent,
    resolve: {
      orderStatus: OrderStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrderStatusUpdateComponent,
    resolve: {
      orderStatus: OrderStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
