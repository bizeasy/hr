import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmployment, Employment } from 'app/shared/model/employment.model';
import { EmploymentService } from './employment.service';
import { EmploymentComponent } from './employment.component';
import { EmploymentDetailComponent } from './employment-detail.component';
import { EmploymentUpdateComponent } from './employment-update.component';

@Injectable({ providedIn: 'root' })
export class EmploymentResolve implements Resolve<IEmployment> {
  constructor(private service: EmploymentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((employment: HttpResponse<Employment>) => {
          if (employment.body) {
            return of(employment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Employment());
  }
}

export const employmentRoute: Routes = [
  {
    path: '',
    component: EmploymentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.employment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmploymentDetailComponent,
    resolve: {
      employment: EmploymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.employment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmploymentUpdateComponent,
    resolve: {
      employment: EmploymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.employment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmploymentUpdateComponent,
    resolve: {
      employment: EmploymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.employment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
