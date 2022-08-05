import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IParty, Party } from 'app/shared/model/party.model';
import { PartyService } from './party.service';
import { PartyComponent } from './party.component';
import { PartyDetailComponent } from './party-detail.component';
import { PartyUpdateComponent } from './party-update.component';

@Injectable({ providedIn: 'root' })
export class PartyResolve implements Resolve<IParty> {
  constructor(private service: PartyService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParty> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((party: HttpResponse<Party>) => {
          if (party.body) {
            return of(party.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Party());
  }
}

export const partyRoute: Routes = [
  {
    path: '',
    component: PartyComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.party.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyDetailComponent,
    resolve: {
      party: PartyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.party.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyUpdateComponent,
    resolve: {
      party: PartyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.party.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyUpdateComponent,
    resolve: {
      party: PartyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.party.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
