import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRoleType, RoleType } from 'app/shared/model/role-type.model';
import { RoleTypeService } from './role-type.service';
import { RoleTypeComponent } from './role-type.component';
import { RoleTypeDetailComponent } from './role-type-detail.component';
import { RoleTypeUpdateComponent } from './role-type-update.component';

@Injectable({ providedIn: 'root' })
export class RoleTypeResolve implements Resolve<IRoleType> {
  constructor(private service: RoleTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRoleType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((roleType: HttpResponse<RoleType>) => {
          if (roleType.body) {
            return of(roleType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RoleType());
  }
}

export const roleTypeRoute: Routes = [
  {
    path: '',
    component: RoleTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.roleType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RoleTypeDetailComponent,
    resolve: {
      roleType: RoleTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.roleType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RoleTypeUpdateComponent,
    resolve: {
      roleType: RoleTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.roleType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RoleTypeUpdateComponent,
    resolve: {
      roleType: RoleTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.roleType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
