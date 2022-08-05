import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDeduction, Deduction } from 'app/shared/model/deduction.model';
import { DeductionService } from './deduction.service';
import { DeductionComponent } from './deduction.component';
import { DeductionDetailComponent } from './deduction-detail.component';
import { DeductionUpdateComponent } from './deduction-update.component';

@Injectable({ providedIn: 'root' })
export class DeductionResolve implements Resolve<IDeduction> {
  constructor(private service: DeductionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeduction> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((deduction: HttpResponse<Deduction>) => {
          if (deduction.body) {
            return of(deduction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Deduction());
  }
}

export const deductionRoute: Routes = [
  {
    path: '',
    component: DeductionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.deduction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DeductionDetailComponent,
    resolve: {
      deduction: DeductionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.deduction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DeductionUpdateComponent,
    resolve: {
      deduction: DeductionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.deduction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DeductionUpdateComponent,
    resolve: {
      deduction: DeductionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.deduction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
