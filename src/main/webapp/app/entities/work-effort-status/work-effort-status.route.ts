import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorkEffortStatus, WorkEffortStatus } from 'app/shared/model/work-effort-status.model';
import { WorkEffortStatusService } from './work-effort-status.service';
import { WorkEffortStatusComponent } from './work-effort-status.component';
import { WorkEffortStatusDetailComponent } from './work-effort-status-detail.component';
import { WorkEffortStatusUpdateComponent } from './work-effort-status-update.component';

@Injectable({ providedIn: 'root' })
export class WorkEffortStatusResolve implements Resolve<IWorkEffortStatus> {
  constructor(private service: WorkEffortStatusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkEffortStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((workEffortStatus: HttpResponse<WorkEffortStatus>) => {
          if (workEffortStatus.body) {
            return of(workEffortStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkEffortStatus());
  }
}

export const workEffortStatusRoute: Routes = [
  {
    path: '',
    component: WorkEffortStatusComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.workEffortStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkEffortStatusDetailComponent,
    resolve: {
      workEffortStatus: WorkEffortStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkEffortStatusUpdateComponent,
    resolve: {
      workEffortStatus: WorkEffortStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkEffortStatusUpdateComponent,
    resolve: {
      workEffortStatus: WorkEffortStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
