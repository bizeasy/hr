import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorkEffortAssoc, WorkEffortAssoc } from 'app/shared/model/work-effort-assoc.model';
import { WorkEffortAssocService } from './work-effort-assoc.service';
import { WorkEffortAssocComponent } from './work-effort-assoc.component';
import { WorkEffortAssocDetailComponent } from './work-effort-assoc-detail.component';
import { WorkEffortAssocUpdateComponent } from './work-effort-assoc-update.component';

@Injectable({ providedIn: 'root' })
export class WorkEffortAssocResolve implements Resolve<IWorkEffortAssoc> {
  constructor(private service: WorkEffortAssocService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkEffortAssoc> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((workEffortAssoc: HttpResponse<WorkEffortAssoc>) => {
          if (workEffortAssoc.body) {
            return of(workEffortAssoc.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkEffortAssoc());
  }
}

export const workEffortAssocRoute: Routes = [
  {
    path: '',
    component: WorkEffortAssocComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.workEffortAssoc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkEffortAssocDetailComponent,
    resolve: {
      workEffortAssoc: WorkEffortAssocResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortAssoc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkEffortAssocUpdateComponent,
    resolve: {
      workEffortAssoc: WorkEffortAssocResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortAssoc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkEffortAssocUpdateComponent,
    resolve: {
      workEffortAssoc: WorkEffortAssocResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortAssoc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
