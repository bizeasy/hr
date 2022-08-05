import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFacilityContactMech, FacilityContactMech } from 'app/shared/model/facility-contact-mech.model';
import { FacilityContactMechService } from './facility-contact-mech.service';
import { FacilityContactMechComponent } from './facility-contact-mech.component';
import { FacilityContactMechDetailComponent } from './facility-contact-mech-detail.component';
import { FacilityContactMechUpdateComponent } from './facility-contact-mech-update.component';

@Injectable({ providedIn: 'root' })
export class FacilityContactMechResolve implements Resolve<IFacilityContactMech> {
  constructor(private service: FacilityContactMechService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFacilityContactMech> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((facilityContactMech: HttpResponse<FacilityContactMech>) => {
          if (facilityContactMech.body) {
            return of(facilityContactMech.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FacilityContactMech());
  }
}

export const facilityContactMechRoute: Routes = [
  {
    path: '',
    component: FacilityContactMechComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.facilityContactMech.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FacilityContactMechDetailComponent,
    resolve: {
      facilityContactMech: FacilityContactMechResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityContactMech.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FacilityContactMechUpdateComponent,
    resolve: {
      facilityContactMech: FacilityContactMechResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityContactMech.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FacilityContactMechUpdateComponent,
    resolve: {
      facilityContactMech: FacilityContactMechResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityContactMech.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
