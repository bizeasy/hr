import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrderTerm, OrderTerm } from 'app/shared/model/order-term.model';
import { OrderTermService } from './order-term.service';
import { OrderTermComponent } from './order-term.component';
import { OrderTermDetailComponent } from './order-term-detail.component';
import { OrderTermUpdateComponent } from './order-term-update.component';

@Injectable({ providedIn: 'root' })
export class OrderTermResolve implements Resolve<IOrderTerm> {
  constructor(private service: OrderTermService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderTerm> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((orderTerm: HttpResponse<OrderTerm>) => {
          if (orderTerm.body) {
            return of(orderTerm.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderTerm());
  }
}

export const orderTermRoute: Routes = [
  {
    path: '',
    component: OrderTermComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderTerm.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrderTermDetailComponent,
    resolve: {
      orderTerm: OrderTermResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderTerm.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrderTermUpdateComponent,
    resolve: {
      orderTerm: OrderTermResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderTerm.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrderTermUpdateComponent,
    resolve: {
      orderTerm: OrderTermResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderTerm.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
