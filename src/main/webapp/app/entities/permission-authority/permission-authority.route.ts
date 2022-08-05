import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPermissionAuthority, PermissionAuthority } from 'app/shared/model/permission-authority.model';
import { PermissionAuthorityService } from './permission-authority.service';
import { PermissionAuthorityComponent } from './permission-authority.component';
import { PermissionAuthorityDetailComponent } from './permission-authority-detail.component';
import { PermissionAuthorityUpdateComponent } from './permission-authority-update.component';

@Injectable({ providedIn: 'root' })
export class PermissionAuthorityResolve implements Resolve<IPermissionAuthority> {
  constructor(private service: PermissionAuthorityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPermissionAuthority> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((permissionAuthority: HttpResponse<PermissionAuthority>) => {
          if (permissionAuthority.body) {
            return of(permissionAuthority.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PermissionAuthority());
  }
}

export const permissionAuthorityRoute: Routes = [
  {
    path: '',
    component: PermissionAuthorityComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.permissionAuthority.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PermissionAuthorityDetailComponent,
    resolve: {
      permissionAuthority: PermissionAuthorityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.permissionAuthority.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PermissionAuthorityUpdateComponent,
    resolve: {
      permissionAuthority: PermissionAuthorityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.permissionAuthority.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PermissionAuthorityUpdateComponent,
    resolve: {
      permissionAuthority: PermissionAuthorityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.permissionAuthority.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
