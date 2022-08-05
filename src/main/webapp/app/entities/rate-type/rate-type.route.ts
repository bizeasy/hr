import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRateType, RateType } from 'app/shared/model/rate-type.model';
import { RateTypeService } from './rate-type.service';
import { RateTypeComponent } from './rate-type.component';
import { RateTypeDetailComponent } from './rate-type-detail.component';
import { RateTypeUpdateComponent } from './rate-type-update.component';

@Injectable({ providedIn: 'root' })
export class RateTypeResolve implements Resolve<IRateType> {
  constructor(private service: RateTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRateType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((rateType: HttpResponse<RateType>) => {
          if (rateType.body) {
            return of(rateType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RateType());
  }
}

export const rateTypeRoute: Routes = [
  {
    path: '',
    component: RateTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.rateType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RateTypeDetailComponent,
    resolve: {
      rateType: RateTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.rateType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RateTypeUpdateComponent,
    resolve: {
      rateType: RateTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.rateType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RateTypeUpdateComponent,
    resolve: {
      rateType: RateTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.rateType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
