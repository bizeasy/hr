import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUomType, UomType } from 'app/shared/model/uom-type.model';
import { UomTypeService } from './uom-type.service';
import { UomTypeComponent } from './uom-type.component';
import { UomTypeDetailComponent } from './uom-type-detail.component';
import { UomTypeUpdateComponent } from './uom-type-update.component';

@Injectable({ providedIn: 'root' })
export class UomTypeResolve implements Resolve<IUomType> {
  constructor(private service: UomTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUomType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((uomType: HttpResponse<UomType>) => {
          if (uomType.body) {
            return of(uomType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UomType());
  }
}

export const uomTypeRoute: Routes = [
  {
    path: '',
    component: UomTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.uomType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UomTypeDetailComponent,
    resolve: {
      uomType: UomTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.uomType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UomTypeUpdateComponent,
    resolve: {
      uomType: UomTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.uomType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UomTypeUpdateComponent,
    resolve: {
      uomType: UomTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.uomType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
