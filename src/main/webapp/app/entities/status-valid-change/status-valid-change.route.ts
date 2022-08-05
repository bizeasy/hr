import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStatusValidChange, StatusValidChange } from 'app/shared/model/status-valid-change.model';
import { StatusValidChangeService } from './status-valid-change.service';
import { StatusValidChangeComponent } from './status-valid-change.component';
import { StatusValidChangeDetailComponent } from './status-valid-change-detail.component';
import { StatusValidChangeUpdateComponent } from './status-valid-change-update.component';

@Injectable({ providedIn: 'root' })
export class StatusValidChangeResolve implements Resolve<IStatusValidChange> {
  constructor(private service: StatusValidChangeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStatusValidChange> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((statusValidChange: HttpResponse<StatusValidChange>) => {
          if (statusValidChange.body) {
            return of(statusValidChange.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StatusValidChange());
  }
}

export const statusValidChangeRoute: Routes = [
  {
    path: '',
    component: StatusValidChangeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.statusValidChange.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StatusValidChangeDetailComponent,
    resolve: {
      statusValidChange: StatusValidChangeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.statusValidChange.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StatusValidChangeUpdateComponent,
    resolve: {
      statusValidChange: StatusValidChangeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.statusValidChange.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StatusValidChangeUpdateComponent,
    resolve: {
      statusValidChange: StatusValidChangeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.statusValidChange.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
