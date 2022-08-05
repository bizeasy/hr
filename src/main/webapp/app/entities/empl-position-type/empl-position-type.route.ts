import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmplPositionType, EmplPositionType } from 'app/shared/model/empl-position-type.model';
import { EmplPositionTypeService } from './empl-position-type.service';
import { EmplPositionTypeComponent } from './empl-position-type.component';
import { EmplPositionTypeDetailComponent } from './empl-position-type-detail.component';
import { EmplPositionTypeUpdateComponent } from './empl-position-type-update.component';

@Injectable({ providedIn: 'root' })
export class EmplPositionTypeResolve implements Resolve<IEmplPositionType> {
  constructor(private service: EmplPositionTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmplPositionType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((emplPositionType: HttpResponse<EmplPositionType>) => {
          if (emplPositionType.body) {
            return of(emplPositionType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmplPositionType());
  }
}

export const emplPositionTypeRoute: Routes = [
  {
    path: '',
    component: EmplPositionTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmplPositionTypeDetailComponent,
    resolve: {
      emplPositionType: EmplPositionTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmplPositionTypeUpdateComponent,
    resolve: {
      emplPositionType: EmplPositionTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmplPositionTypeUpdateComponent,
    resolve: {
      emplPositionType: EmplPositionTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
