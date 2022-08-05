import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrderContactMech, OrderContactMech } from 'app/shared/model/order-contact-mech.model';
import { OrderContactMechService } from './order-contact-mech.service';
import { OrderContactMechComponent } from './order-contact-mech.component';
import { OrderContactMechDetailComponent } from './order-contact-mech-detail.component';
import { OrderContactMechUpdateComponent } from './order-contact-mech-update.component';

@Injectable({ providedIn: 'root' })
export class OrderContactMechResolve implements Resolve<IOrderContactMech> {
  constructor(private service: OrderContactMechService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderContactMech> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((orderContactMech: HttpResponse<OrderContactMech>) => {
          if (orderContactMech.body) {
            return of(orderContactMech.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderContactMech());
  }
}

export const orderContactMechRoute: Routes = [
  {
    path: '',
    component: OrderContactMechComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.orderContactMech.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrderContactMechDetailComponent,
    resolve: {
      orderContactMech: OrderContactMechResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderContactMech.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrderContactMechUpdateComponent,
    resolve: {
      orderContactMech: OrderContactMechResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderContactMech.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrderContactMechUpdateComponent,
    resolve: {
      orderContactMech: OrderContactMechResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.orderContactMech.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
