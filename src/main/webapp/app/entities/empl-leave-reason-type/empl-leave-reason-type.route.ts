import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmplLeaveReasonType, EmplLeaveReasonType } from 'app/shared/model/empl-leave-reason-type.model';
import { EmplLeaveReasonTypeService } from './empl-leave-reason-type.service';
import { EmplLeaveReasonTypeComponent } from './empl-leave-reason-type.component';
import { EmplLeaveReasonTypeDetailComponent } from './empl-leave-reason-type-detail.component';
import { EmplLeaveReasonTypeUpdateComponent } from './empl-leave-reason-type-update.component';

@Injectable({ providedIn: 'root' })
export class EmplLeaveReasonTypeResolve implements Resolve<IEmplLeaveReasonType> {
  constructor(private service: EmplLeaveReasonTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmplLeaveReasonType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((emplLeaveReasonType: HttpResponse<EmplLeaveReasonType>) => {
          if (emplLeaveReasonType.body) {
            return of(emplLeaveReasonType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmplLeaveReasonType());
  }
}

export const emplLeaveReasonTypeRoute: Routes = [
  {
    path: '',
    component: EmplLeaveReasonTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplLeaveReasonType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmplLeaveReasonTypeDetailComponent,
    resolve: {
      emplLeaveReasonType: EmplLeaveReasonTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplLeaveReasonType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmplLeaveReasonTypeUpdateComponent,
    resolve: {
      emplLeaveReasonType: EmplLeaveReasonTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplLeaveReasonType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmplLeaveReasonTypeUpdateComponent,
    resolve: {
      emplLeaveReasonType: EmplLeaveReasonTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplLeaveReasonType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
