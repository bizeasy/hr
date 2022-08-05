import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IShiftHolidays, ShiftHolidays } from 'app/shared/model/shift-holidays.model';
import { ShiftHolidaysService } from './shift-holidays.service';
import { ShiftHolidaysComponent } from './shift-holidays.component';
import { ShiftHolidaysDetailComponent } from './shift-holidays-detail.component';
import { ShiftHolidaysUpdateComponent } from './shift-holidays-update.component';

@Injectable({ providedIn: 'root' })
export class ShiftHolidaysResolve implements Resolve<IShiftHolidays> {
  constructor(private service: ShiftHolidaysService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IShiftHolidays> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((shiftHolidays: HttpResponse<ShiftHolidays>) => {
          if (shiftHolidays.body) {
            return of(shiftHolidays.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ShiftHolidays());
  }
}

export const shiftHolidaysRoute: Routes = [
  {
    path: '',
    component: ShiftHolidaysComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.shiftHolidays.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShiftHolidaysDetailComponent,
    resolve: {
      shiftHolidays: ShiftHolidaysResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.shiftHolidays.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShiftHolidaysUpdateComponent,
    resolve: {
      shiftHolidays: ShiftHolidaysResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.shiftHolidays.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShiftHolidaysUpdateComponent,
    resolve: {
      shiftHolidays: ShiftHolidaysResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.shiftHolidays.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
