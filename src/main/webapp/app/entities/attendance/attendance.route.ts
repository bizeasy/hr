import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAttendance, Attendance } from 'app/shared/model/attendance.model';
import { AttendanceService } from './attendance.service';
import { AttendanceComponent } from './attendance.component';
import { AttendanceDetailComponent } from './attendance-detail.component';
import { AttendanceUpdateComponent } from './attendance-update.component';

@Injectable({ providedIn: 'root' })
export class AttendanceResolve implements Resolve<IAttendance> {
  constructor(private service: AttendanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttendance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((attendance: HttpResponse<Attendance>) => {
          if (attendance.body) {
            return of(attendance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Attendance());
  }
}

export const attendanceRoute: Routes = [
  {
    path: '',
    component: AttendanceComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.attendance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AttendanceDetailComponent,
    resolve: {
      attendance: AttendanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.attendance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AttendanceUpdateComponent,
    resolve: {
      attendance: AttendanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.attendance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AttendanceUpdateComponent,
    resolve: {
      attendance: AttendanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.attendance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
