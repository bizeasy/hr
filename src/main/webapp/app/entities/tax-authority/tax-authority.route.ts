import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaxAuthority, TaxAuthority } from 'app/shared/model/tax-authority.model';
import { TaxAuthorityService } from './tax-authority.service';
import { TaxAuthorityComponent } from './tax-authority.component';
import { TaxAuthorityDetailComponent } from './tax-authority-detail.component';
import { TaxAuthorityUpdateComponent } from './tax-authority-update.component';

@Injectable({ providedIn: 'root' })
export class TaxAuthorityResolve implements Resolve<ITaxAuthority> {
  constructor(private service: TaxAuthorityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaxAuthority> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taxAuthority: HttpResponse<TaxAuthority>) => {
          if (taxAuthority.body) {
            return of(taxAuthority.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaxAuthority());
  }
}

export const taxAuthorityRoute: Routes = [
  {
    path: '',
    component: TaxAuthorityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.taxAuthority.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaxAuthorityDetailComponent,
    resolve: {
      taxAuthority: TaxAuthorityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.taxAuthority.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaxAuthorityUpdateComponent,
    resolve: {
      taxAuthority: TaxAuthorityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.taxAuthority.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaxAuthorityUpdateComponent,
    resolve: {
      taxAuthority: TaxAuthorityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.taxAuthority.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
