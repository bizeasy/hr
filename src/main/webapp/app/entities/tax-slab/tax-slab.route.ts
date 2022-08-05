import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaxSlab, TaxSlab } from 'app/shared/model/tax-slab.model';
import { TaxSlabService } from './tax-slab.service';
import { TaxSlabComponent } from './tax-slab.component';
import { TaxSlabDetailComponent } from './tax-slab-detail.component';
import { TaxSlabUpdateComponent } from './tax-slab-update.component';

@Injectable({ providedIn: 'root' })
export class TaxSlabResolve implements Resolve<ITaxSlab> {
  constructor(private service: TaxSlabService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaxSlab> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taxSlab: HttpResponse<TaxSlab>) => {
          if (taxSlab.body) {
            return of(taxSlab.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaxSlab());
  }
}

export const taxSlabRoute: Routes = [
  {
    path: '',
    component: TaxSlabComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.taxSlab.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaxSlabDetailComponent,
    resolve: {
      taxSlab: TaxSlabResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.taxSlab.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaxSlabUpdateComponent,
    resolve: {
      taxSlab: TaxSlabResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.taxSlab.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaxSlabUpdateComponent,
    resolve: {
      taxSlab: TaxSlabResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.taxSlab.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
