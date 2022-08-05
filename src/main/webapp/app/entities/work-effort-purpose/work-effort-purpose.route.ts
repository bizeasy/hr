import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorkEffortPurpose, WorkEffortPurpose } from 'app/shared/model/work-effort-purpose.model';
import { WorkEffortPurposeService } from './work-effort-purpose.service';
import { WorkEffortPurposeComponent } from './work-effort-purpose.component';
import { WorkEffortPurposeDetailComponent } from './work-effort-purpose-detail.component';
import { WorkEffortPurposeUpdateComponent } from './work-effort-purpose-update.component';

@Injectable({ providedIn: 'root' })
export class WorkEffortPurposeResolve implements Resolve<IWorkEffortPurpose> {
  constructor(private service: WorkEffortPurposeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkEffortPurpose> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((workEffortPurpose: HttpResponse<WorkEffortPurpose>) => {
          if (workEffortPurpose.body) {
            return of(workEffortPurpose.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkEffortPurpose());
  }
}

export const workEffortPurposeRoute: Routes = [
  {
    path: '',
    component: WorkEffortPurposeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortPurpose.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkEffortPurposeDetailComponent,
    resolve: {
      workEffortPurpose: WorkEffortPurposeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortPurpose.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkEffortPurposeUpdateComponent,
    resolve: {
      workEffortPurpose: WorkEffortPurposeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortPurpose.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkEffortPurposeUpdateComponent,
    resolve: {
      workEffortPurpose: WorkEffortPurposeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortPurpose.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
