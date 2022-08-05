import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IShiftWeekends, ShiftWeekends } from 'app/shared/model/shift-weekends.model';
import { ShiftWeekendsService } from './shift-weekends.service';
import { ShiftWeekendsComponent } from './shift-weekends.component';
import { ShiftWeekendsDetailComponent } from './shift-weekends-detail.component';
import { ShiftWeekendsUpdateComponent } from './shift-weekends-update.component';

@Injectable({ providedIn: 'root' })
export class ShiftWeekendsResolve implements Resolve<IShiftWeekends> {
  constructor(private service: ShiftWeekendsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IShiftWeekends> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((shiftWeekends: HttpResponse<ShiftWeekends>) => {
          if (shiftWeekends.body) {
            return of(shiftWeekends.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ShiftWeekends());
  }
}

export const shiftWeekendsRoute: Routes = [
  {
    path: '',
    component: ShiftWeekendsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.shiftWeekends.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShiftWeekendsDetailComponent,
    resolve: {
      shiftWeekends: ShiftWeekendsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.shiftWeekends.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShiftWeekendsUpdateComponent,
    resolve: {
      shiftWeekends: ShiftWeekendsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.shiftWeekends.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShiftWeekendsUpdateComponent,
    resolve: {
      shiftWeekends: ShiftWeekendsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.shiftWeekends.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
