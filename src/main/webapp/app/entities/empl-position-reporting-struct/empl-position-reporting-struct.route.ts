import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmplPositionReportingStruct, EmplPositionReportingStruct } from 'app/shared/model/empl-position-reporting-struct.model';
import { EmplPositionReportingStructService } from './empl-position-reporting-struct.service';
import { EmplPositionReportingStructComponent } from './empl-position-reporting-struct.component';
import { EmplPositionReportingStructDetailComponent } from './empl-position-reporting-struct-detail.component';
import { EmplPositionReportingStructUpdateComponent } from './empl-position-reporting-struct-update.component';

@Injectable({ providedIn: 'root' })
export class EmplPositionReportingStructResolve implements Resolve<IEmplPositionReportingStruct> {
  constructor(private service: EmplPositionReportingStructService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmplPositionReportingStruct> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((emplPositionReportingStruct: HttpResponse<EmplPositionReportingStruct>) => {
          if (emplPositionReportingStruct.body) {
            return of(emplPositionReportingStruct.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmplPositionReportingStruct());
  }
}

export const emplPositionReportingStructRoute: Routes = [
  {
    path: '',
    component: EmplPositionReportingStructComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionReportingStruct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmplPositionReportingStructDetailComponent,
    resolve: {
      emplPositionReportingStruct: EmplPositionReportingStructResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionReportingStruct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmplPositionReportingStructUpdateComponent,
    resolve: {
      emplPositionReportingStruct: EmplPositionReportingStructResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionReportingStruct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmplPositionReportingStructUpdateComponent,
    resolve: {
      emplPositionReportingStruct: EmplPositionReportingStructResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionReportingStruct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
