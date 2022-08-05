import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFacilityGroupMember, FacilityGroupMember } from 'app/shared/model/facility-group-member.model';
import { FacilityGroupMemberService } from './facility-group-member.service';
import { FacilityGroupMemberComponent } from './facility-group-member.component';
import { FacilityGroupMemberDetailComponent } from './facility-group-member-detail.component';
import { FacilityGroupMemberUpdateComponent } from './facility-group-member-update.component';

@Injectable({ providedIn: 'root' })
export class FacilityGroupMemberResolve implements Resolve<IFacilityGroupMember> {
  constructor(private service: FacilityGroupMemberService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFacilityGroupMember> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((facilityGroupMember: HttpResponse<FacilityGroupMember>) => {
          if (facilityGroupMember.body) {
            return of(facilityGroupMember.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FacilityGroupMember());
  }
}

export const facilityGroupMemberRoute: Routes = [
  {
    path: '',
    component: FacilityGroupMemberComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.facilityGroupMember.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FacilityGroupMemberDetailComponent,
    resolve: {
      facilityGroupMember: FacilityGroupMemberResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityGroupMember.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FacilityGroupMemberUpdateComponent,
    resolve: {
      facilityGroupMember: FacilityGroupMemberResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityGroupMember.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FacilityGroupMemberUpdateComponent,
    resolve: {
      facilityGroupMember: FacilityGroupMemberResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityGroupMember.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
