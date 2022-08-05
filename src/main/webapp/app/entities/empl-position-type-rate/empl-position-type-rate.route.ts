import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmplPositionTypeRate, EmplPositionTypeRate } from 'app/shared/model/empl-position-type-rate.model';
import { EmplPositionTypeRateService } from './empl-position-type-rate.service';
import { EmplPositionTypeRateComponent } from './empl-position-type-rate.component';
import { EmplPositionTypeRateDetailComponent } from './empl-position-type-rate-detail.component';
import { EmplPositionTypeRateUpdateComponent } from './empl-position-type-rate-update.component';

@Injectable({ providedIn: 'root' })
export class EmplPositionTypeRateResolve implements Resolve<IEmplPositionTypeRate> {
  constructor(private service: EmplPositionTypeRateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmplPositionTypeRate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((emplPositionTypeRate: HttpResponse<EmplPositionTypeRate>) => {
          if (emplPositionTypeRate.body) {
            return of(emplPositionTypeRate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmplPositionTypeRate());
  }
}

export const emplPositionTypeRateRoute: Routes = [
  {
    path: '',
    component: EmplPositionTypeRateComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionTypeRate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmplPositionTypeRateDetailComponent,
    resolve: {
      emplPositionTypeRate: EmplPositionTypeRateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionTypeRate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmplPositionTypeRateUpdateComponent,
    resolve: {
      emplPositionTypeRate: EmplPositionTypeRateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionTypeRate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmplPositionTypeRateUpdateComponent,
    resolve: {
      emplPositionTypeRate: EmplPositionTypeRateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionTypeRate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
