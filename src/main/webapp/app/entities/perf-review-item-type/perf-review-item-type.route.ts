import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPerfReviewItemType, PerfReviewItemType } from 'app/shared/model/perf-review-item-type.model';
import { PerfReviewItemTypeService } from './perf-review-item-type.service';
import { PerfReviewItemTypeComponent } from './perf-review-item-type.component';
import { PerfReviewItemTypeDetailComponent } from './perf-review-item-type-detail.component';
import { PerfReviewItemTypeUpdateComponent } from './perf-review-item-type-update.component';

@Injectable({ providedIn: 'root' })
export class PerfReviewItemTypeResolve implements Resolve<IPerfReviewItemType> {
  constructor(private service: PerfReviewItemTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPerfReviewItemType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((perfReviewItemType: HttpResponse<PerfReviewItemType>) => {
          if (perfReviewItemType.body) {
            return of(perfReviewItemType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PerfReviewItemType());
  }
}

export const perfReviewItemTypeRoute: Routes = [
  {
    path: '',
    component: PerfReviewItemTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.perfReviewItemType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PerfReviewItemTypeDetailComponent,
    resolve: {
      perfReviewItemType: PerfReviewItemTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.perfReviewItemType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PerfReviewItemTypeUpdateComponent,
    resolve: {
      perfReviewItemType: PerfReviewItemTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.perfReviewItemType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PerfReviewItemTypeUpdateComponent,
    resolve: {
      perfReviewItemType: PerfReviewItemTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.perfReviewItemType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
