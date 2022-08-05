import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartyClassificationGroup, PartyClassificationGroup } from 'app/shared/model/party-classification-group.model';
import { PartyClassificationGroupService } from './party-classification-group.service';
import { PartyClassificationGroupComponent } from './party-classification-group.component';
import { PartyClassificationGroupDetailComponent } from './party-classification-group-detail.component';
import { PartyClassificationGroupUpdateComponent } from './party-classification-group-update.component';

@Injectable({ providedIn: 'root' })
export class PartyClassificationGroupResolve implements Resolve<IPartyClassificationGroup> {
  constructor(private service: PartyClassificationGroupService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyClassificationGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partyClassificationGroup: HttpResponse<PartyClassificationGroup>) => {
          if (partyClassificationGroup.body) {
            return of(partyClassificationGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyClassificationGroup());
  }
}

export const partyClassificationGroupRoute: Routes = [
  {
    path: '',
    component: PartyClassificationGroupComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyClassificationGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyClassificationGroupDetailComponent,
    resolve: {
      partyClassificationGroup: PartyClassificationGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyClassificationGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyClassificationGroupUpdateComponent,
    resolve: {
      partyClassificationGroup: PartyClassificationGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyClassificationGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyClassificationGroupUpdateComponent,
    resolve: {
      partyClassificationGroup: PartyClassificationGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyClassificationGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
