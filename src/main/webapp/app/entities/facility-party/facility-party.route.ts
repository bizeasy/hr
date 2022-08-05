import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFacilityParty, FacilityParty } from 'app/shared/model/facility-party.model';
import { FacilityPartyService } from './facility-party.service';
import { FacilityPartyComponent } from './facility-party.component';
import { FacilityPartyDetailComponent } from './facility-party-detail.component';
import { FacilityPartyUpdateComponent } from './facility-party-update.component';

@Injectable({ providedIn: 'root' })
export class FacilityPartyResolve implements Resolve<IFacilityParty> {
  constructor(private service: FacilityPartyService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFacilityParty> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((facilityParty: HttpResponse<FacilityParty>) => {
          if (facilityParty.body) {
            return of(facilityParty.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FacilityParty());
  }
}

export const facilityPartyRoute: Routes = [
  {
    path: '',
    component: FacilityPartyComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.facilityParty.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FacilityPartyDetailComponent,
    resolve: {
      facilityParty: FacilityPartyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityParty.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FacilityPartyUpdateComponent,
    resolve: {
      facilityParty: FacilityPartyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityParty.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FacilityPartyUpdateComponent,
    resolve: {
      facilityParty: FacilityPartyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityParty.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
