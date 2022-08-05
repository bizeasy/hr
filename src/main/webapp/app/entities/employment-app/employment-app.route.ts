import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmploymentApp, EmploymentApp } from 'app/shared/model/employment-app.model';
import { EmploymentAppService } from './employment-app.service';
import { EmploymentAppComponent } from './employment-app.component';
import { EmploymentAppDetailComponent } from './employment-app-detail.component';
import { EmploymentAppUpdateComponent } from './employment-app-update.component';

@Injectable({ providedIn: 'root' })
export class EmploymentAppResolve implements Resolve<IEmploymentApp> {
  constructor(private service: EmploymentAppService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmploymentApp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((employmentApp: HttpResponse<EmploymentApp>) => {
          if (employmentApp.body) {
            return of(employmentApp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmploymentApp());
  }
}

export const employmentAppRoute: Routes = [
  {
    path: '',
    component: EmploymentAppComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.employmentApp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmploymentAppDetailComponent,
    resolve: {
      employmentApp: EmploymentAppResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.employmentApp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmploymentAppUpdateComponent,
    resolve: {
      employmentApp: EmploymentAppResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.employmentApp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmploymentAppUpdateComponent,
    resolve: {
      employmentApp: EmploymentAppResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.employmentApp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
