import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaxAuthorityRateType, TaxAuthorityRateType } from 'app/shared/model/tax-authority-rate-type.model';
import { TaxAuthorityRateTypeService } from './tax-authority-rate-type.service';
import { TaxAuthorityRateTypeComponent } from './tax-authority-rate-type.component';
import { TaxAuthorityRateTypeDetailComponent } from './tax-authority-rate-type-detail.component';
import { TaxAuthorityRateTypeUpdateComponent } from './tax-authority-rate-type-update.component';

@Injectable({ providedIn: 'root' })
export class TaxAuthorityRateTypeResolve implements Resolve<ITaxAuthorityRateType> {
  constructor(private service: TaxAuthorityRateTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaxAuthorityRateType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taxAuthorityRateType: HttpResponse<TaxAuthorityRateType>) => {
          if (taxAuthorityRateType.body) {
            return of(taxAuthorityRateType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaxAuthorityRateType());
  }
}

export const taxAuthorityRateTypeRoute: Routes = [
  {
    path: '',
    component: TaxAuthorityRateTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.taxAuthorityRateType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaxAuthorityRateTypeDetailComponent,
    resolve: {
      taxAuthorityRateType: TaxAuthorityRateTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.taxAuthorityRateType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaxAuthorityRateTypeUpdateComponent,
    resolve: {
      taxAuthorityRateType: TaxAuthorityRateTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.taxAuthorityRateType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaxAuthorityRateTypeUpdateComponent,
    resolve: {
      taxAuthorityRateType: TaxAuthorityRateTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.taxAuthorityRateType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
