import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmploymentAppSourceType, EmploymentAppSourceType } from 'app/shared/model/employment-app-source-type.model';
import { EmploymentAppSourceTypeService } from './employment-app-source-type.service';
import { EmploymentAppSourceTypeComponent } from './employment-app-source-type.component';
import { EmploymentAppSourceTypeDetailComponent } from './employment-app-source-type-detail.component';
import { EmploymentAppSourceTypeUpdateComponent } from './employment-app-source-type-update.component';

@Injectable({ providedIn: 'root' })
export class EmploymentAppSourceTypeResolve implements Resolve<IEmploymentAppSourceType> {
  constructor(private service: EmploymentAppSourceTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmploymentAppSourceType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((employmentAppSourceType: HttpResponse<EmploymentAppSourceType>) => {
          if (employmentAppSourceType.body) {
            return of(employmentAppSourceType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmploymentAppSourceType());
  }
}

export const employmentAppSourceTypeRoute: Routes = [
  {
    path: '',
    component: EmploymentAppSourceTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.employmentAppSourceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmploymentAppSourceTypeDetailComponent,
    resolve: {
      employmentAppSourceType: EmploymentAppSourceTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.employmentAppSourceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmploymentAppSourceTypeUpdateComponent,
    resolve: {
      employmentAppSourceType: EmploymentAppSourceTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.employmentAppSourceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmploymentAppSourceTypeUpdateComponent,
    resolve: {
      employmentAppSourceType: EmploymentAppSourceTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.employmentAppSourceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
