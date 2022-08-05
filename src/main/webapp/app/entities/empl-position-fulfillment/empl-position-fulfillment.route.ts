import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmplPositionFulfillment, EmplPositionFulfillment } from 'app/shared/model/empl-position-fulfillment.model';
import { EmplPositionFulfillmentService } from './empl-position-fulfillment.service';
import { EmplPositionFulfillmentComponent } from './empl-position-fulfillment.component';
import { EmplPositionFulfillmentDetailComponent } from './empl-position-fulfillment-detail.component';
import { EmplPositionFulfillmentUpdateComponent } from './empl-position-fulfillment-update.component';

@Injectable({ providedIn: 'root' })
export class EmplPositionFulfillmentResolve implements Resolve<IEmplPositionFulfillment> {
  constructor(private service: EmplPositionFulfillmentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmplPositionFulfillment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((emplPositionFulfillment: HttpResponse<EmplPositionFulfillment>) => {
          if (emplPositionFulfillment.body) {
            return of(emplPositionFulfillment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmplPositionFulfillment());
  }
}

export const emplPositionFulfillmentRoute: Routes = [
  {
    path: '',
    component: EmplPositionFulfillmentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionFulfillment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmplPositionFulfillmentDetailComponent,
    resolve: {
      emplPositionFulfillment: EmplPositionFulfillmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionFulfillment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmplPositionFulfillmentUpdateComponent,
    resolve: {
      emplPositionFulfillment: EmplPositionFulfillmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionFulfillment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmplPositionFulfillmentUpdateComponent,
    resolve: {
      emplPositionFulfillment: EmplPositionFulfillmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionFulfillment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
