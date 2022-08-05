import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDeductionType, DeductionType } from 'app/shared/model/deduction-type.model';
import { DeductionTypeService } from './deduction-type.service';
import { DeductionTypeComponent } from './deduction-type.component';
import { DeductionTypeDetailComponent } from './deduction-type-detail.component';
import { DeductionTypeUpdateComponent } from './deduction-type-update.component';

@Injectable({ providedIn: 'root' })
export class DeductionTypeResolve implements Resolve<IDeductionType> {
  constructor(private service: DeductionTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeductionType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((deductionType: HttpResponse<DeductionType>) => {
          if (deductionType.body) {
            return of(deductionType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DeductionType());
  }
}

export const deductionTypeRoute: Routes = [
  {
    path: '',
    component: DeductionTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.deductionType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DeductionTypeDetailComponent,
    resolve: {
      deductionType: DeductionTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.deductionType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DeductionTypeUpdateComponent,
    resolve: {
      deductionType: DeductionTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.deductionType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DeductionTypeUpdateComponent,
    resolve: {
      deductionType: DeductionTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.deductionType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
