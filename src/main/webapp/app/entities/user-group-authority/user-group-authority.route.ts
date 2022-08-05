import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserGroupAuthority, UserGroupAuthority } from 'app/shared/model/user-group-authority.model';
import { UserGroupAuthorityService } from './user-group-authority.service';
import { UserGroupAuthorityComponent } from './user-group-authority.component';
import { UserGroupAuthorityDetailComponent } from './user-group-authority-detail.component';
import { UserGroupAuthorityUpdateComponent } from './user-group-authority-update.component';

@Injectable({ providedIn: 'root' })
export class UserGroupAuthorityResolve implements Resolve<IUserGroupAuthority> {
  constructor(private service: UserGroupAuthorityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserGroupAuthority> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userGroupAuthority: HttpResponse<UserGroupAuthority>) => {
          if (userGroupAuthority.body) {
            return of(userGroupAuthority.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserGroupAuthority());
  }
}

export const userGroupAuthorityRoute: Routes = [
  {
    path: '',
    component: UserGroupAuthorityComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.userGroupAuthority.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserGroupAuthorityDetailComponent,
    resolve: {
      userGroupAuthority: UserGroupAuthorityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.userGroupAuthority.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserGroupAuthorityUpdateComponent,
    resolve: {
      userGroupAuthority: UserGroupAuthorityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.userGroupAuthority.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserGroupAuthorityUpdateComponent,
    resolve: {
      userGroupAuthority: UserGroupAuthorityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.userGroupAuthority.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
