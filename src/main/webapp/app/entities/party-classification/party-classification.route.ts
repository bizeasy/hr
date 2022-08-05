import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartyClassification, PartyClassification } from 'app/shared/model/party-classification.model';
import { PartyClassificationService } from './party-classification.service';
import { PartyClassificationComponent } from './party-classification.component';
import { PartyClassificationDetailComponent } from './party-classification-detail.component';
import { PartyClassificationUpdateComponent } from './party-classification-update.component';

@Injectable({ providedIn: 'root' })
export class PartyClassificationResolve implements Resolve<IPartyClassification> {
  constructor(private service: PartyClassificationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyClassification> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partyClassification: HttpResponse<PartyClassification>) => {
          if (partyClassification.body) {
            return of(partyClassification.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyClassification());
  }
}

export const partyClassificationRoute: Routes = [
  {
    path: '',
    component: PartyClassificationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.partyClassification.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyClassificationDetailComponent,
    resolve: {
      partyClassification: PartyClassificationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyClassification.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyClassificationUpdateComponent,
    resolve: {
      partyClassification: PartyClassificationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyClassification.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyClassificationUpdateComponent,
    resolve: {
      partyClassification: PartyClassificationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyClassification.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
