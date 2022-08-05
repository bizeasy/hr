import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPeriodType, PeriodType } from 'app/shared/model/period-type.model';
import { PeriodTypeService } from './period-type.service';
import { PeriodTypeComponent } from './period-type.component';
import { PeriodTypeDetailComponent } from './period-type-detail.component';
import { PeriodTypeUpdateComponent } from './period-type-update.component';

@Injectable({ providedIn: 'root' })
export class PeriodTypeResolve implements Resolve<IPeriodType> {
  constructor(private service: PeriodTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPeriodType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((periodType: HttpResponse<PeriodType>) => {
          if (periodType.body) {
            return of(periodType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PeriodType());
  }
}

export const periodTypeRoute: Routes = [
  {
    path: '',
    component: PeriodTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.periodType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PeriodTypeDetailComponent,
    resolve: {
      periodType: PeriodTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.periodType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PeriodTypeUpdateComponent,
    resolve: {
      periodType: PeriodTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.periodType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PeriodTypeUpdateComponent,
    resolve: {
      periodType: PeriodTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.periodType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
