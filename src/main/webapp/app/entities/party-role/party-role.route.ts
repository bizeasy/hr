import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartyRole, PartyRole } from 'app/shared/model/party-role.model';
import { PartyRoleService } from './party-role.service';
import { PartyRoleComponent } from './party-role.component';
import { PartyRoleDetailComponent } from './party-role-detail.component';
import { PartyRoleUpdateComponent } from './party-role-update.component';

@Injectable({ providedIn: 'root' })
export class PartyRoleResolve implements Resolve<IPartyRole> {
  constructor(private service: PartyRoleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyRole> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partyRole: HttpResponse<PartyRole>) => {
          if (partyRole.body) {
            return of(partyRole.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyRole());
  }
}

export const partyRoleRoute: Routes = [
  {
    path: '',
    component: PartyRoleComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.partyRole.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyRoleDetailComponent,
    resolve: {
      partyRole: PartyRoleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyRole.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyRoleUpdateComponent,
    resolve: {
      partyRole: PartyRoleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyRole.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyRoleUpdateComponent,
    resolve: {
      partyRole: PartyRoleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyRole.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
