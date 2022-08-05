import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUom, Uom } from 'app/shared/model/uom.model';
import { UomService } from './uom.service';
import { UomComponent } from './uom.component';
import { UomDetailComponent } from './uom-detail.component';
import { UomUpdateComponent } from './uom-update.component';

@Injectable({ providedIn: 'root' })
export class UomResolve implements Resolve<IUom> {
  constructor(private service: UomService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUom> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((uom: HttpResponse<Uom>) => {
          if (uom.body) {
            return of(uom.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Uom());
  }
}

export const uomRoute: Routes = [
  {
    path: '',
    component: UomComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.uom.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UomDetailComponent,
    resolve: {
      uom: UomResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.uom.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UomUpdateComponent,
    resolve: {
      uom: UomResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.uom.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UomUpdateComponent,
    resolve: {
      uom: UomResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.uom.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
