import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmplLeave, EmplLeave } from 'app/shared/model/empl-leave.model';
import { EmplLeaveService } from './empl-leave.service';
import { EmplLeaveComponent } from './empl-leave.component';
import { EmplLeaveDetailComponent } from './empl-leave-detail.component';
import { EmplLeaveUpdateComponent } from './empl-leave-update.component';

@Injectable({ providedIn: 'root' })
export class EmplLeaveResolve implements Resolve<IEmplLeave> {
  constructor(private service: EmplLeaveService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmplLeave> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((emplLeave: HttpResponse<EmplLeave>) => {
          if (emplLeave.body) {
            return of(emplLeave.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmplLeave());
  }
}

export const emplLeaveRoute: Routes = [
  {
    path: '',
    component: EmplLeaveComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplLeave.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmplLeaveDetailComponent,
    resolve: {
      emplLeave: EmplLeaveResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplLeave.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmplLeaveUpdateComponent,
    resolve: {
      emplLeave: EmplLeaveResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplLeave.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmplLeaveUpdateComponent,
    resolve: {
      emplLeave: EmplLeaveResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplLeave.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
