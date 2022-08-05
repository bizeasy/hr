import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorkEffortType, WorkEffortType } from 'app/shared/model/work-effort-type.model';
import { WorkEffortTypeService } from './work-effort-type.service';
import { WorkEffortTypeComponent } from './work-effort-type.component';
import { WorkEffortTypeDetailComponent } from './work-effort-type-detail.component';
import { WorkEffortTypeUpdateComponent } from './work-effort-type-update.component';

@Injectable({ providedIn: 'root' })
export class WorkEffortTypeResolve implements Resolve<IWorkEffortType> {
  constructor(private service: WorkEffortTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkEffortType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((workEffortType: HttpResponse<WorkEffortType>) => {
          if (workEffortType.body) {
            return of(workEffortType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkEffortType());
  }
}

export const workEffortTypeRoute: Routes = [
  {
    path: '',
    component: WorkEffortTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkEffortTypeDetailComponent,
    resolve: {
      workEffortType: WorkEffortTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkEffortTypeUpdateComponent,
    resolve: {
      workEffortType: WorkEffortTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkEffortTypeUpdateComponent,
    resolve: {
      workEffortType: WorkEffortTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
