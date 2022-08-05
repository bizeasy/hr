import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartyContactMech, PartyContactMech } from 'app/shared/model/party-contact-mech.model';
import { PartyContactMechService } from './party-contact-mech.service';
import { PartyContactMechComponent } from './party-contact-mech.component';
import { PartyContactMechDetailComponent } from './party-contact-mech-detail.component';
import { PartyContactMechUpdateComponent } from './party-contact-mech-update.component';

@Injectable({ providedIn: 'root' })
export class PartyContactMechResolve implements Resolve<IPartyContactMech> {
  constructor(private service: PartyContactMechService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyContactMech> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partyContactMech: HttpResponse<PartyContactMech>) => {
          if (partyContactMech.body) {
            return of(partyContactMech.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyContactMech());
  }
}

export const partyContactMechRoute: Routes = [
  {
    path: '',
    component: PartyContactMechComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.partyContactMech.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyContactMechDetailComponent,
    resolve: {
      partyContactMech: PartyContactMechResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyContactMech.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyContactMechUpdateComponent,
    resolve: {
      partyContactMech: PartyContactMechResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyContactMech.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyContactMechUpdateComponent,
    resolve: {
      partyContactMech: PartyContactMechResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyContactMech.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
