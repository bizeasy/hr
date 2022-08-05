import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmplLeaveType, EmplLeaveType } from 'app/shared/model/empl-leave-type.model';
import { EmplLeaveTypeService } from './empl-leave-type.service';
import { EmplLeaveTypeComponent } from './empl-leave-type.component';
import { EmplLeaveTypeDetailComponent } from './empl-leave-type-detail.component';
import { EmplLeaveTypeUpdateComponent } from './empl-leave-type-update.component';

@Injectable({ providedIn: 'root' })
export class EmplLeaveTypeResolve implements Resolve<IEmplLeaveType> {
  constructor(private service: EmplLeaveTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmplLeaveType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((emplLeaveType: HttpResponse<EmplLeaveType>) => {
          if (emplLeaveType.body) {
            return of(emplLeaveType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmplLeaveType());
  }
}

export const emplLeaveTypeRoute: Routes = [
  {
    path: '',
    component: EmplLeaveTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplLeaveType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmplLeaveTypeDetailComponent,
    resolve: {
      emplLeaveType: EmplLeaveTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplLeaveType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmplLeaveTypeUpdateComponent,
    resolve: {
      emplLeaveType: EmplLeaveTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplLeaveType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmplLeaveTypeUpdateComponent,
    resolve: {
      emplLeaveType: EmplLeaveTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplLeaveType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
