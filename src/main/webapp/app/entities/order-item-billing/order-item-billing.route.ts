import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrderItemBilling, OrderItemBilling } from 'app/shared/model/order-item-billing.model';
import { OrderItemBillingService } from './order-item-billing.service';
import { OrderItemBillingComponent } from './order-item-billing.component';
import { OrderItemBillingDetailComponent } from './order-item-billing-detail.component';
import { OrderItemBillingUpdateComponent } from './order-item-billing-update.component';

@Injectable({ providedIn: 'root' })
export class OrderItemBillingResolve implements Resolve<IOrderItemBilling> {
  constructor(private service: OrderItemBillingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderItemBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((orderItemBilling: HttpResponse<OrderItemBilling>) => {
          if (orderItemBilling.body) {
            return of(orderItemBilling.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderItemBilling());
  }
}

export const orderItemBillingRoute: Routes = [
  {
    path: '',
    component: OrderItemBillingComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.orderItemBilling.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrderItemBillingDetailComponent,
    resolve: {
      orderItemBilling: OrderItemBillingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderItemBilling.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrderItemBillingUpdateComponent,
    resolve: {
      orderItemBilling: OrderItemBillingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderItemBilling.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrderItemBillingUpdateComponent,
    resolve: {
      orderItemBilling: OrderItemBillingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderItemBilling.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
