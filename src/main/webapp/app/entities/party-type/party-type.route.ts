import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartyType, PartyType } from 'app/shared/model/party-type.model';
import { PartyTypeService } from './party-type.service';
import { PartyTypeComponent } from './party-type.component';
import { PartyTypeDetailComponent } from './party-type-detail.component';
import { PartyTypeUpdateComponent } from './party-type-update.component';

@Injectable({ providedIn: 'root' })
export class PartyTypeResolve implements Resolve<IPartyType> {
  constructor(private service: PartyTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partyType: HttpResponse<PartyType>) => {
          if (partyType.body) {
            return of(partyType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyType());
  }
}

export const partyTypeRoute: Routes = [
  {
    path: '',
    component: PartyTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyTypeDetailComponent,
    resolve: {
      partyType: PartyTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyTypeUpdateComponent,
    resolve: {
      partyType: PartyTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyTypeUpdateComponent,
    resolve: {
      partyType: PartyTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
