import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICustomTimePeriod, CustomTimePeriod } from 'app/shared/model/custom-time-period.model';
import { CustomTimePeriodService } from './custom-time-period.service';
import { CustomTimePeriodComponent } from './custom-time-period.component';
import { CustomTimePeriodDetailComponent } from './custom-time-period-detail.component';
import { CustomTimePeriodUpdateComponent } from './custom-time-period-update.component';

@Injectable({ providedIn: 'root' })
export class CustomTimePeriodResolve implements Resolve<ICustomTimePeriod> {
  constructor(private service: CustomTimePeriodService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomTimePeriod> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((customTimePeriod: HttpResponse<CustomTimePeriod>) => {
          if (customTimePeriod.body) {
            return of(customTimePeriod.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomTimePeriod());
  }
}

export const customTimePeriodRoute: Routes = [
  {
    path: '',
    component: CustomTimePeriodComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.customTimePeriod.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomTimePeriodDetailComponent,
    resolve: {
      customTimePeriod: CustomTimePeriodResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.customTimePeriod.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomTimePeriodUpdateComponent,
    resolve: {
      customTimePeriod: CustomTimePeriodResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.customTimePeriod.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomTimePeriodUpdateComponent,
    resolve: {
      customTimePeriod: CustomTimePeriodResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.customTimePeriod.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
