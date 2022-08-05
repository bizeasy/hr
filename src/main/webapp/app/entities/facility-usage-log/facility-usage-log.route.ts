import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFacilityUsageLog, FacilityUsageLog } from 'app/shared/model/facility-usage-log.model';
import { FacilityUsageLogService } from './facility-usage-log.service';
import { FacilityUsageLogComponent } from './facility-usage-log.component';
import { FacilityUsageLogDetailComponent } from './facility-usage-log-detail.component';
import { FacilityUsageLogUpdateComponent } from './facility-usage-log-update.component';

@Injectable({ providedIn: 'root' })
export class FacilityUsageLogResolve implements Resolve<IFacilityUsageLog> {
  constructor(private service: FacilityUsageLogService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFacilityUsageLog> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((facilityUsageLog: HttpResponse<FacilityUsageLog>) => {
          if (facilityUsageLog.body) {
            return of(facilityUsageLog.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FacilityUsageLog());
  }
}

export const facilityUsageLogRoute: Routes = [
  {
    path: '',
    component: FacilityUsageLogComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityUsageLog.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FacilityUsageLogDetailComponent,
    resolve: {
      facilityUsageLog: FacilityUsageLogResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityUsageLog.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FacilityUsageLogUpdateComponent,
    resolve: {
      facilityUsageLog: FacilityUsageLogResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityUsageLog.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FacilityUsageLogUpdateComponent,
    resolve: {
      facilityUsageLog: FacilityUsageLogResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityUsageLog.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
