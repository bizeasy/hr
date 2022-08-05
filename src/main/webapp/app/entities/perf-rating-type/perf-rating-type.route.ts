import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPerfRatingType, PerfRatingType } from 'app/shared/model/perf-rating-type.model';
import { PerfRatingTypeService } from './perf-rating-type.service';
import { PerfRatingTypeComponent } from './perf-rating-type.component';
import { PerfRatingTypeDetailComponent } from './perf-rating-type-detail.component';
import { PerfRatingTypeUpdateComponent } from './perf-rating-type-update.component';

@Injectable({ providedIn: 'root' })
export class PerfRatingTypeResolve implements Resolve<IPerfRatingType> {
  constructor(private service: PerfRatingTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPerfRatingType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((perfRatingType: HttpResponse<PerfRatingType>) => {
          if (perfRatingType.body) {
            return of(perfRatingType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PerfRatingType());
  }
}

export const perfRatingTypeRoute: Routes = [
  {
    path: '',
    component: PerfRatingTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.perfRatingType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PerfRatingTypeDetailComponent,
    resolve: {
      perfRatingType: PerfRatingTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.perfRatingType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PerfRatingTypeUpdateComponent,
    resolve: {
      perfRatingType: PerfRatingTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.perfRatingType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PerfRatingTypeUpdateComponent,
    resolve: {
      perfRatingType: PerfRatingTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.perfRatingType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
