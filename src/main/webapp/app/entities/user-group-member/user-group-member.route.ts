import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserGroupMember, UserGroupMember } from 'app/shared/model/user-group-member.model';
import { UserGroupMemberService } from './user-group-member.service';
import { UserGroupMemberComponent } from './user-group-member.component';
import { UserGroupMemberDetailComponent } from './user-group-member-detail.component';
import { UserGroupMemberUpdateComponent } from './user-group-member-update.component';

@Injectable({ providedIn: 'root' })
export class UserGroupMemberResolve implements Resolve<IUserGroupMember> {
  constructor(private service: UserGroupMemberService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserGroupMember> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userGroupMember: HttpResponse<UserGroupMember>) => {
          if (userGroupMember.body) {
            return of(userGroupMember.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserGroupMember());
  }
}

export const userGroupMemberRoute: Routes = [
  {
    path: '',
    component: UserGroupMemberComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.userGroupMember.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserGroupMemberDetailComponent,
    resolve: {
      userGroupMember: UserGroupMemberResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.userGroupMember.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserGroupMemberUpdateComponent,
    resolve: {
      userGroupMember: UserGroupMemberResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.userGroupMember.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserGroupMemberUpdateComponent,
    resolve: {
      userGroupMember: UserGroupMemberResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.userGroupMember.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
