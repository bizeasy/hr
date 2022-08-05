import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPerfReviewItem, PerfReviewItem } from 'app/shared/model/perf-review-item.model';
import { PerfReviewItemService } from './perf-review-item.service';
import { PerfReviewItemComponent } from './perf-review-item.component';
import { PerfReviewItemDetailComponent } from './perf-review-item-detail.component';
import { PerfReviewItemUpdateComponent } from './perf-review-item-update.component';

@Injectable({ providedIn: 'root' })
export class PerfReviewItemResolve implements Resolve<IPerfReviewItem> {
  constructor(private service: PerfReviewItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPerfReviewItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((perfReviewItem: HttpResponse<PerfReviewItem>) => {
          if (perfReviewItem.body) {
            return of(perfReviewItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PerfReviewItem());
  }
}

export const perfReviewItemRoute: Routes = [
  {
    path: '',
    component: PerfReviewItemComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.perfReviewItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PerfReviewItemDetailComponent,
    resolve: {
      perfReviewItem: PerfReviewItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.perfReviewItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PerfReviewItemUpdateComponent,
    resolve: {
      perfReviewItem: PerfReviewItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.perfReviewItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PerfReviewItemUpdateComponent,
    resolve: {
      perfReviewItem: PerfReviewItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.perfReviewItem.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
