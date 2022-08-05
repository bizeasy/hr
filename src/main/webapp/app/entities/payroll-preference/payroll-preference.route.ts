import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPayrollPreference, PayrollPreference } from 'app/shared/model/payroll-preference.model';
import { PayrollPreferenceService } from './payroll-preference.service';
import { PayrollPreferenceComponent } from './payroll-preference.component';
import { PayrollPreferenceDetailComponent } from './payroll-preference-detail.component';
import { PayrollPreferenceUpdateComponent } from './payroll-preference-update.component';

@Injectable({ providedIn: 'root' })
export class PayrollPreferenceResolve implements Resolve<IPayrollPreference> {
  constructor(private service: PayrollPreferenceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPayrollPreference> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((payrollPreference: HttpResponse<PayrollPreference>) => {
          if (payrollPreference.body) {
            return of(payrollPreference.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PayrollPreference());
  }
}

export const payrollPreferenceRoute: Routes = [
  {
    path: '',
    component: PayrollPreferenceComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.payrollPreference.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PayrollPreferenceDetailComponent,
    resolve: {
      payrollPreference: PayrollPreferenceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.payrollPreference.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PayrollPreferenceUpdateComponent,
    resolve: {
      payrollPreference: PayrollPreferenceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.payrollPreference.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PayrollPreferenceUpdateComponent,
    resolve: {
      payrollPreference: PayrollPreferenceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.payrollPreference.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
