import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartyClassificationType, PartyClassificationType } from 'app/shared/model/party-classification-type.model';
import { PartyClassificationTypeService } from './party-classification-type.service';
import { PartyClassificationTypeComponent } from './party-classification-type.component';
import { PartyClassificationTypeDetailComponent } from './party-classification-type-detail.component';
import { PartyClassificationTypeUpdateComponent } from './party-classification-type-update.component';

@Injectable({ providedIn: 'root' })
export class PartyClassificationTypeResolve implements Resolve<IPartyClassificationType> {
  constructor(private service: PartyClassificationTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyClassificationType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partyClassificationType: HttpResponse<PartyClassificationType>) => {
          if (partyClassificationType.body) {
            return of(partyClassificationType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyClassificationType());
  }
}

export const partyClassificationTypeRoute: Routes = [
  {
    path: '',
    component: PartyClassificationTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyClassificationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyClassificationTypeDetailComponent,
    resolve: {
      partyClassificationType: PartyClassificationTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyClassificationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyClassificationTypeUpdateComponent,
    resolve: {
      partyClassificationType: PartyClassificationTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyClassificationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyClassificationTypeUpdateComponent,
    resolve: {
      partyClassificationType: PartyClassificationTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyClassificationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
