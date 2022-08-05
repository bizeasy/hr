import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPerfReview, PerfReview } from 'app/shared/model/perf-review.model';
import { PerfReviewService } from './perf-review.service';
import { PerfReviewComponent } from './perf-review.component';
import { PerfReviewDetailComponent } from './perf-review-detail.component';
import { PerfReviewUpdateComponent } from './perf-review-update.component';

@Injectable({ providedIn: 'root' })
export class PerfReviewResolve implements Resolve<IPerfReview> {
  constructor(private service: PerfReviewService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPerfReview> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((perfReview: HttpResponse<PerfReview>) => {
          if (perfReview.body) {
            return of(perfReview.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PerfReview());
  }
}

export const perfReviewRoute: Routes = [
  {
    path: '',
    component: PerfReviewComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.perfReview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PerfReviewDetailComponent,
    resolve: {
      perfReview: PerfReviewResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.perfReview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PerfReviewUpdateComponent,
    resolve: {
      perfReview: PerfReviewResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.perfReview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PerfReviewUpdateComponent,
    resolve: {
      perfReview: PerfReviewResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.perfReview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
