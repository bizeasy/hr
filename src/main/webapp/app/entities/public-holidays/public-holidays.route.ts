import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPublicHolidays, PublicHolidays } from 'app/shared/model/public-holidays.model';
import { PublicHolidaysService } from './public-holidays.service';
import { PublicHolidaysComponent } from './public-holidays.component';
import { PublicHolidaysDetailComponent } from './public-holidays-detail.component';
import { PublicHolidaysUpdateComponent } from './public-holidays-update.component';

@Injectable({ providedIn: 'root' })
export class PublicHolidaysResolve implements Resolve<IPublicHolidays> {
  constructor(private service: PublicHolidaysService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPublicHolidays> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((publicHolidays: HttpResponse<PublicHolidays>) => {
          if (publicHolidays.body) {
            return of(publicHolidays.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PublicHolidays());
  }
}

export const publicHolidaysRoute: Routes = [
  {
    path: '',
    component: PublicHolidaysComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.publicHolidays.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PublicHolidaysDetailComponent,
    resolve: {
      publicHolidays: PublicHolidaysResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.publicHolidays.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PublicHolidaysUpdateComponent,
    resolve: {
      publicHolidays: PublicHolidaysResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.publicHolidays.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PublicHolidaysUpdateComponent,
    resolve: {
      publicHolidays: PublicHolidaysResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.publicHolidays.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
