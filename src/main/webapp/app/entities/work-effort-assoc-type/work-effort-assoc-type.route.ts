import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorkEffortAssocType, WorkEffortAssocType } from 'app/shared/model/work-effort-assoc-type.model';
import { WorkEffortAssocTypeService } from './work-effort-assoc-type.service';
import { WorkEffortAssocTypeComponent } from './work-effort-assoc-type.component';
import { WorkEffortAssocTypeDetailComponent } from './work-effort-assoc-type-detail.component';
import { WorkEffortAssocTypeUpdateComponent } from './work-effort-assoc-type-update.component';

@Injectable({ providedIn: 'root' })
export class WorkEffortAssocTypeResolve implements Resolve<IWorkEffortAssocType> {
  constructor(private service: WorkEffortAssocTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkEffortAssocType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((workEffortAssocType: HttpResponse<WorkEffortAssocType>) => {
          if (workEffortAssocType.body) {
            return of(workEffortAssocType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkEffortAssocType());
  }
}

export const workEffortAssocTypeRoute: Routes = [
  {
    path: '',
    component: WorkEffortAssocTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortAssocType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkEffortAssocTypeDetailComponent,
    resolve: {
      workEffortAssocType: WorkEffortAssocTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortAssocType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkEffortAssocTypeUpdateComponent,
    resolve: {
      workEffortAssocType: WorkEffortAssocTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortAssocType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkEffortAssocTypeUpdateComponent,
    resolve: {
      workEffortAssocType: WorkEffortAssocTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortAssocType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
