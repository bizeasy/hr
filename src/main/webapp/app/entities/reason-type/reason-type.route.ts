import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReasonType, ReasonType } from 'app/shared/model/reason-type.model';
import { ReasonTypeService } from './reason-type.service';
import { ReasonTypeComponent } from './reason-type.component';
import { ReasonTypeDetailComponent } from './reason-type-detail.component';
import { ReasonTypeUpdateComponent } from './reason-type-update.component';

@Injectable({ providedIn: 'root' })
export class ReasonTypeResolve implements Resolve<IReasonType> {
  constructor(private service: ReasonTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReasonType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((reasonType: HttpResponse<ReasonType>) => {
          if (reasonType.body) {
            return of(reasonType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ReasonType());
  }
}

export const reasonTypeRoute: Routes = [
  {
    path: '',
    component: ReasonTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.reasonType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReasonTypeDetailComponent,
    resolve: {
      reasonType: ReasonTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.reasonType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReasonTypeUpdateComponent,
    resolve: {
      reasonType: ReasonTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.reasonType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReasonTypeUpdateComponent,
    resolve: {
      reasonType: ReasonTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.reasonType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
