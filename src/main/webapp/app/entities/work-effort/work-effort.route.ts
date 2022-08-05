import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorkEffort, WorkEffort } from 'app/shared/model/work-effort.model';
import { WorkEffortService } from './work-effort.service';
import { WorkEffortComponent } from './work-effort.component';
import { WorkEffortDetailComponent } from './work-effort-detail.component';
import { WorkEffortUpdateComponent } from './work-effort-update.component';

@Injectable({ providedIn: 'root' })
export class WorkEffortResolve implements Resolve<IWorkEffort> {
  constructor(private service: WorkEffortService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkEffort> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((workEffort: HttpResponse<WorkEffort>) => {
          if (workEffort.body) {
            return of(workEffort.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkEffort());
  }
}

export const workEffortRoute: Routes = [
  {
    path: '',
    component: WorkEffortComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.workEffort.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkEffortDetailComponent,
    resolve: {
      workEffort: WorkEffortResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffort.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkEffortUpdateComponent,
    resolve: {
      workEffort: WorkEffortResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffort.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkEffortUpdateComponent,
    resolve: {
      workEffort: WorkEffortResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffort.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
