import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorkEffortInventoryAssign, WorkEffortInventoryAssign } from 'app/shared/model/work-effort-inventory-assign.model';
import { WorkEffortInventoryAssignService } from './work-effort-inventory-assign.service';
import { WorkEffortInventoryAssignComponent } from './work-effort-inventory-assign.component';
import { WorkEffortInventoryAssignDetailComponent } from './work-effort-inventory-assign-detail.component';
import { WorkEffortInventoryAssignUpdateComponent } from './work-effort-inventory-assign-update.component';

@Injectable({ providedIn: 'root' })
export class WorkEffortInventoryAssignResolve implements Resolve<IWorkEffortInventoryAssign> {
  constructor(private service: WorkEffortInventoryAssignService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkEffortInventoryAssign> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((workEffortInventoryAssign: HttpResponse<WorkEffortInventoryAssign>) => {
          if (workEffortInventoryAssign.body) {
            return of(workEffortInventoryAssign.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkEffortInventoryAssign());
  }
}

export const workEffortInventoryAssignRoute: Routes = [
  {
    path: '',
    component: WorkEffortInventoryAssignComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.workEffortInventoryAssign.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkEffortInventoryAssignDetailComponent,
    resolve: {
      workEffortInventoryAssign: WorkEffortInventoryAssignResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortInventoryAssign.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkEffortInventoryAssignUpdateComponent,
    resolve: {
      workEffortInventoryAssign: WorkEffortInventoryAssignResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortInventoryAssign.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkEffortInventoryAssignUpdateComponent,
    resolve: {
      workEffortInventoryAssign: WorkEffortInventoryAssignResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.workEffortInventoryAssign.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
