import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITimeSheet, TimeSheet } from 'app/shared/model/time-sheet.model';
import { TimeSheetService } from './time-sheet.service';
import { TimeSheetComponent } from './time-sheet.component';
import { TimeSheetDetailComponent } from './time-sheet-detail.component';
import { TimeSheetUpdateComponent } from './time-sheet-update.component';

@Injectable({ providedIn: 'root' })
export class TimeSheetResolve implements Resolve<ITimeSheet> {
  constructor(private service: TimeSheetService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITimeSheet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((timeSheet: HttpResponse<TimeSheet>) => {
          if (timeSheet.body) {
            return of(timeSheet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TimeSheet());
  }
}

export const timeSheetRoute: Routes = [
  {
    path: '',
    component: TimeSheetComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.timeSheet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TimeSheetDetailComponent,
    resolve: {
      timeSheet: TimeSheetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.timeSheet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TimeSheetUpdateComponent,
    resolve: {
      timeSheet: TimeSheetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.timeSheet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TimeSheetUpdateComponent,
    resolve: {
      timeSheet: TimeSheetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.timeSheet.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
