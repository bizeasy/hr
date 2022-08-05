import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrderItemType, OrderItemType } from 'app/shared/model/order-item-type.model';
import { OrderItemTypeService } from './order-item-type.service';
import { OrderItemTypeComponent } from './order-item-type.component';
import { OrderItemTypeDetailComponent } from './order-item-type-detail.component';
import { OrderItemTypeUpdateComponent } from './order-item-type-update.component';

@Injectable({ providedIn: 'root' })
export class OrderItemTypeResolve implements Resolve<IOrderItemType> {
  constructor(private service: OrderItemTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderItemType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((orderItemType: HttpResponse<OrderItemType>) => {
          if (orderItemType.body) {
            return of(orderItemType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderItemType());
  }
}

export const orderItemTypeRoute: Routes = [
  {
    path: '',
    component: OrderItemTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderItemType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrderItemTypeDetailComponent,
    resolve: {
      orderItemType: OrderItemTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderItemType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrderItemTypeUpdateComponent,
    resolve: {
      orderItemType: OrderItemTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderItemType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrderItemTypeUpdateComponent,
    resolve: {
      orderItemType: OrderItemTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderItemType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
